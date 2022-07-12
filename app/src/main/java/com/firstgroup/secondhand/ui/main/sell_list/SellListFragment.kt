package com.firstgroup.secondhand.ui.main.sell_list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.domain.order.OrderFilter
import com.firstgroup.secondhand.ui.auth.AuthActivity
import com.firstgroup.secondhand.ui.auth.LoginState
import com.firstgroup.secondhand.ui.components.GenericLoadingScreen
import com.firstgroup.secondhand.ui.components.ListBidProduct
import com.firstgroup.secondhand.ui.components.ListProduct
import com.firstgroup.secondhand.ui.components.LoginLayoutPlaceholder
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SellListFragment : Fragment() {

    private val viewModel: SellListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsState()
                MdcTheme {
                    SellListScreen(
                        uiState = uiState,
                        onLoginClick = ::goToLoginScreen,
                        viewModel = viewModel,
                        onProductClick = {
                            findNavController().navigate(
                                SellListFragmentDirections.actionMainNavigationSellListToDetailFragment(
                                    it
                                )
                            )
                        },
                        onOrderClick = {
                            findNavController().navigate(
                                SellListFragmentDirections.actionMainNavigationSellListToDetailBidderFragment(
                                    it
                                )
                            )
                        }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession()
    }

    private fun goToLoginScreen() {
        startActivity(Intent(requireContext(), AuthActivity::class.java))
    }
}

@Composable
fun SellListScreen(
    uiState: SellListUiState,
    onLoginClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    onOrderClick: (Int) -> Unit,
    viewModel: SellListViewModel
) {
    LaunchedEffect(key1 = uiState.loginState) {
        if (uiState.loginState is LoginState.Loaded) {
            if (uiState.loginState.isLoggedIn) {
                viewModel.getProductAsSeller()
                viewModel.getOrderAsSeller(viewModel.filter.value)
            }
        }
    }
    when (uiState.loginState) {
        is LoginState.Idle -> {
            GenericLoadingScreen()
        }
        is LoginState.Loaded -> {
            if (uiState.loginState.isLoggedIn) {
                SellListScreen(
                    uiState = uiState,
                    onProductClick = onProductClick,
                    onOrderClick = onOrderClick,
                    viewModel = viewModel
                )
            } else {
                LoginLayoutPlaceholder(onButtonClick = onLoginClick)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SellListScreen(
    uiState: SellListUiState,
    onProductClick: (Int) -> Unit,
    onOrderClick: (Int) -> Unit,
    viewModel: SellListViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
            val pages = remember {
                listOf(R.string.product, R.string.order)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                val coroutineScope = rememberCoroutineScope()
                // remember a PagerState
                val pagerState = rememberPagerState()

                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                        )
                    },
                    backgroundColor = MaterialTheme.colors.background,
                    contentColor = MaterialTheme.colors.primary
                ) {
                    pages.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(text = stringResource(title)) },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                        )
                    }
                }
                HorizontalPager(
                    count = pages.size,
                    state = pagerState,
                    contentPadding = PaddingValues(vertical = 16.dp),
                    modifier = Modifier
                        .fillMaxWidth(),
                ) { page ->
                    Column(modifier = Modifier.fillMaxSize()) {
                        when (page) {
                            0 -> {
                                uiState.product?.let {
                                    ListProduct(
                                        products = it,
                                        onProductClick = onProductClick
                                    )
                                }
                            }
                            1 -> {

                                OrderFilterDropDown(onFilterSelected = { viewModel.setFilter(it) })
                                Spacer(modifier = Modifier.height(16.dp))
                                uiState.order?.let {
                                    ListBidProduct(
                                        orders = it,
                                        onOrderClick = onOrderClick
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderFilterDropDown(
    onFilterSelected: (OrderFilter) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var filterText by remember { mutableStateOf(getDropdownTitle(OrderFilter.ALlOrders)) }
    val filter: List<OrderFilter> = listOf(
        OrderFilter.ALlOrders,
        OrderFilter.AcceptedOrders,
        OrderFilter.DeclinedOrders,
        OrderFilter.PendingOrders
    )
    Column {
        Text(
            text = stringResource(id = filterText),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clickable { expanded = true },
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold
            )
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filter.forEach {
                DropdownMenuItem(onClick = {
                    onFilterSelected(it)
                    expanded = false
                    filterText = getDropdownTitle(it)

                }) {
                    Text(text = stringResource(id = getDropdownTitle(it)))
                }
            }
        }
    }
}

private fun getDropdownTitle(selectedFilter: OrderFilter) = when (selectedFilter) {
    OrderFilter.ALlOrders -> R.string.all_orders
    OrderFilter.AcceptedOrders -> R.string.accepted_orders
    OrderFilter.DeclinedOrders -> R.string.declined_orders
    OrderFilter.PendingOrders -> R.string.pending_orders
}
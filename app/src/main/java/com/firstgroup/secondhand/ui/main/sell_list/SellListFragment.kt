package com.firstgroup.secondhand.ui.main.sell_list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.core.network.order.model.GetOrderDto
import com.firstgroup.secondhand.ui.auth.AuthActivity
import com.firstgroup.secondhand.ui.auth.LoginState
import com.firstgroup.secondhand.ui.components.GenericLoadingScreen
import com.firstgroup.secondhand.ui.components.ListProduct
import com.firstgroup.secondhand.ui.components.LoginLayoutPlaceholder
import com.firstgroup.secondhand.ui.main.sell_list.detail.dummyOrder
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

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
                        onUserLoggedIn = viewModel::getUser
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession()
    }

    private fun goToLoginScreen(){
        startActivity(Intent(requireContext(), AuthActivity::class.java))
    }
}

@Composable
fun SellListScreen(
    uiState: SellListUiState,
    onLoginClick: () -> Unit,
    onUserLoggedIn: () -> Unit,
    viewModel: SellListViewModel
){
    LaunchedEffect(key1 = uiState.loginState) {
        if (uiState.loginState is LoginState.Loaded) {
            if (uiState.loginState.isLoggedIn) {
                onUserLoggedIn.invoke()
            }
        }
    }
    when (uiState.loginState) {
        is LoginState.Idle -> {
            GenericLoadingScreen()
        }
        is LoginState.Loaded -> {
            if (uiState.loginState.isLoggedIn){
                if (uiState.recentUser != null) {
                    viewModel.getProductAsSeller()
                    SellListScreen(uiState = uiState)
                }
                else {
                    GenericLoadingScreen()
                }
            }
            else {
                LoginLayoutPlaceholder(onButtonClick = onLoginClick)
            }
        }
    }
}

@Composable
fun SellListScreen(
    uiState: SellListUiState
) {
    var selectedIndex by remember { mutableStateOf(1) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Daftar Jual Saya",
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        LazyRow(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
                .height(44.dp)
        ) {
            val listingsCategories: List<Category> =
                listOf(Category(1, "All Listings"), Category(2, "On Bid"), Category(3, "Sold"))
            items(
                items = listingsCategories,
                key = { it.id }
            ) {
                Button(
                    onClick = {
                        selectedIndex = it.id
                    },
                    modifier = Modifier
                        .height(44.dp)
                        .padding(end = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = if (it.id != selectedIndex) ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        backgroundColor = colorResource(id = R.color.dark_blue_01)
                    ) else ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        backgroundColor = colorResource(id = R.color.dark_blue_04)
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        tint = if (it.id != selectedIndex) Color.Black else Color.White,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = it.name, style = MaterialTheme.typography.button)
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        when(selectedIndex){
            1 -> {
                uiState.product?.let { ListProduct(products = it, onProductClick = { }) }
            }
            2 -> {
                ListBidProduct()
            }
            3 -> {

            }
        }
    }
}

@Composable
fun ListBidProduct(){
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        val listDummyOrder : List<GetOrderDto> = listOf(dummyOrder)
        items(
            items = listDummyOrder,
            key = {it.id}
        ) {
            Column {
                if (it.id != listDummyOrder[0].id) {
                    Divider(color = Color.Gray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    // image product
                    val painterProductImage = rememberAsyncImagePainter(
                        model = dummyOrder.product.imageUrl
                    )
                    Image(
                        painter = painterProductImage,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    // product detail : name, price, bid
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = stringResource(R.string.product_offer),
                                modifier = Modifier.fillMaxWidth(0.5f),
                                style = MaterialTheme.typography.body2.copy(
                                    color = Color.Gray
                                )
                            )
                            Text(
                                text = "16 Apr 14:04", // it should date, but no date in order response
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.body2.copy(
                                    color = Color.Gray
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = dummyOrder.product.name,
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        // product normal price
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Rp ${dummyOrder.product.basePrice}",
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.LineThrough
                            )
                        )
                        // buyer bid for selected product
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Ditawar Rp ${dummyOrder.price}",
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
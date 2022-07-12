package com.firstgroup.secondhand.ui.main.home.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.ui.components.GenericLoadingScreen
import com.firstgroup.secondhand.ui.components.ListProduct
import com.firstgroup.secondhand.ui.components.ListProductLoadingScreen
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    SearchScreen(
                        viewModel = viewModel,
                        backToHomePage = ::navigateToHomePage,
                        onProductClick = {}
                    )
                }
            }
        }
    }

    private fun navigateToHomePage() {
        findNavController().navigate(R.id.action_searchFragment_to_main_navigation_home)
    }

}

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    backToHomePage: () -> Unit,
    onProductClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    SearchScreen(
        uiState = uiState,
        backToHomePage = backToHomePage,
        onProductClick = onProductClick,
        searchProducts = viewModel::searchProducts
    )

}

@Composable
fun SearchScreen(
    uiState: SearchUiState,
    backToHomePage: () -> Unit,
    onProductClick: (Int) -> Unit,
    searchProducts: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var searchQuery by remember { mutableStateOf("") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
                .padding(top = 8.dp, bottom = 8.dp, end = 8.dp, start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = backToHomePage,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_left),
                    contentDescription = stringResource(id = R.string.description_back_button)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            TextField( // outlined
                value = searchQuery,
                onValueChange = { searchQuery = it },
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Jam tangan T-rex..",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = MaterialTheme.colors.primary,
                ),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        modifier = Modifier.clickable(
                            enabled = true,
                            onClick = { searchProducts(searchQuery) })
                    )
                }
            )
        }
        Column {
            when (uiState.searchProductState) {
                is ProductsBySearchState.Error -> {

                }
                is ProductsBySearchState.Loading -> {
                    ListProductLoadingScreen()
                }
                is ProductsBySearchState.Success -> {
                    ListProduct(
                        products = uiState.searchProductState.products,
                        onProductClick = onProductClick
                    )
                }
            }
        }
    }
}
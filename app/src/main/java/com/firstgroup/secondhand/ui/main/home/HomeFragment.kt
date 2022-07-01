package com.firstgroup.secondhand.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.ui.components.ListProduct
import com.firstgroup.secondhand.ui.components.ListProductLoadingScreen
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsState()
                val products = viewModel.products.collectAsLazyPagingItems()

                MdcTheme {
                    HomeScreen(
                        uiState = uiState,
                        products = products,
                        onProductClick = {
                            findNavController().navigate(
                                HomeFragmentDirections.actionMainNavigationHomeToDetailFragment(it)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    products: LazyPagingItems<Product>,
    onProductClick: (Int) -> Unit,
) {
    var search by remember { mutableStateOf("") }
    Column {
        Box(
            modifier = Modifier
                .height(398.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = R.drawable.img_banner),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(398.dp)
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent, Color.White
                            ), startY = 500f
                        )
                    )
            )
            Column(modifier = Modifier.fillMaxHeight()) {
                TextField(
                    value = search,
                    onValueChange = { search = it },
                    textStyle = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 38.dp),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    placeholder = {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Cari di Second Chance",
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.fillMaxWidth(0.9f)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null,
                                modifier = Modifier.clickable(onClick = {

                                })
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        placeholderColor = colorResource(id = R.color.neutral_02),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                )
                Spacer(modifier = Modifier.height(200.dp))
                Text(
                    text = "Telusuri Kategori",
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                )
                val categories = mutableListOf(Category(-1, "Semua"))
                when (uiState.categoryState) {
                    is CategoriesUiState.Error -> {
                        Box(Modifier.fillMaxWidth()) {
                            Text(text = "Error")
                        }
                    }
                    is CategoriesUiState.Loading -> {
                        ListCategory(category = dummyCategories, isLoading = true)
                    }
                    is CategoriesUiState.Success -> {
                        categories.addAll(uiState.categoryState.categories)
                        ListCategory(category = categories, isLoading = false)
                    }
                }
            }
        }

        when (uiState.productState) {
            BuyerProductsUiState.Loading -> {
                ListProductLoadingScreen()
            }
            BuyerProductsUiState.Loaded -> {
                ListProduct(
                    products = products,
                    onProductClick = onProductClick,
                )
            }
        }
    }
}

@Composable
fun ListCategory(
    category: List<Category>,
    isLoading: Boolean,
) {
    var selectedIndex by remember { mutableStateOf(-1) }
    LazyRow(
        modifier = Modifier
            .padding(start = 8.dp)
            .height(44.dp)
    ) {
        items(
            items = category,
            key = { it.id }
        ) {
            Button(
                onClick = {
                    selectedIndex = if (selectedIndex != it.id) it.id else -1
                },
                modifier = Modifier
                    .height(44.dp)
                    .padding(horizontal = 8.dp)
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.shimmer(),
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = if (it.id != selectedIndex) buttonColors(
                    contentColor = Color.Black,
                    backgroundColor = colorResource(id = R.color.dark_blue_01)
                ) else buttonColors(
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
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun HomePreview() {
//    MdcTheme {
//        HomeScreen()
//    }
//}

val dummyCategories: List<Category> = List(10) {
    Category(it, "elektronik")
}
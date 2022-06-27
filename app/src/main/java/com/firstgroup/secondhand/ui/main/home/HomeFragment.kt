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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.core.model.Product
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

                MdcTheme {
                    HomeScreen(
                        uiState = uiState,
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
//                if (homeUiState.categoryState is CategoriesUiState.Success) {
//                    ListCategory(category = categories)
//                }
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
            is BuyerProductsUiState.Error -> {
                Box(Modifier.fillMaxWidth()) {
                    Text(text = "Error")
                }
            }
            is BuyerProductsUiState.Loading -> {
                ListProduct(
                    products = dummyProducts,
                    isLoading = true,
                    onProductClick = {}
                )
            }
            is BuyerProductsUiState.Success -> {
                ListProduct(
                    products = uiState.productState.products,
                    isLoading = false,
                    onProductClick = onProductClick
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

@Composable
fun ListProduct(
    products: List<Product>,
    isLoading: Boolean,
    onProductClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
    ) {
        items(products, key = { it.id }) { product ->
            ProductItem(
                product = product,
                isLoading = isLoading,
                onClick = onProductClick,
            )
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    isLoading: Boolean,
    onClick: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .size(width = 156.dp, height = 206.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .background(color = Color.White)
            .clickable { onClick(product.id) },
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            val painter = rememberAsyncImagePainter(
                model = product.imageUrl ?: dummyProduct.imageUrl
            )
            val isImageLoading = painter.state is AsyncImagePainter.State.Loading

            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(height = 100.dp)
                    .fillMaxWidth()
                    .padding(all = 4.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .placeholder(
                        visible = isLoading || isImageLoading,
                        highlight = PlaceholderHighlight.shimmer(),
                    )
            )
            Text(
                text = product.name,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.shimmer(),
                    ),
            )
            Text(
                text = product.category,
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "Rp. ${product.price}",
                modifier = Modifier.padding(all = 8.dp),
                style = MaterialTheme.typography.body1
            )
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

val dummyProduct = Product(
    1,
    "lenovo",
    "ww",
    50000,
    "https://firebasestorage.googleapis.com/v0/b/market-final-project.appspot.com/o/banner%2FBAN-1655129268343-gundam00.jpg?alt=media",
    "bekasi",
    2,
    "new",
    "elektronik"
)

val dummyProducts: List<Product> = List(10) {
    Product(
        it,
        "lenovo",
        "ww",
        50000,
        "https://firebasestorage.googleapis.com/v0/b/market-final-project.appspot.com/o/banner%2FBAN-1655129268343-gundam00.jpg?alt=media",
        "bekasi",
        2,
        "new",
        "elektronik"
    )
}

val dummyCategories: List<Category> = List(10) {
    Category(it, "elektronik")
}
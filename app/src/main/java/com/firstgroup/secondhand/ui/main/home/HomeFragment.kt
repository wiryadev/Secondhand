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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.ui.components.ListProduct
import com.firstgroup.secondhand.ui.components.ListProductLoadingScreen
import com.firstgroup.secondhand.utils.TestTag
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
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
                        onCategorySelected = viewModel::setCategory,
                        onProductClick = ::navigateToProductDetail,
                        onSearchClick = ::navigateToSearchPage
                    )
                }
            }
        }
    }

    private fun navigateToSearchPage() {
        findNavController().navigate(R.id.action_main_navigation_home_to_searchFragment)
    }

    private fun navigateToProductDetail(id: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionMainNavigationHomeToDetailFragment(id)
        )
    }

}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    products: LazyPagingItems<Product>,
    onCategorySelected: (Category) -> Unit,
    onProductClick: (Int) -> Unit,
    onSearchClick: () -> Unit
) {
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
                    .testTag(TestTag.BANNER_SECTION)
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
            Column(
                modifier = Modifier.fillMaxHeight()
                    .testTag(TestTag.FAKE_SEARCH_BAR)
            ) {
                // fake search bar
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 32.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colors.background)
                        .clickable(onClick = onSearchClick)
                        .padding(all = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.search_second_hand),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                    )
                }
                Spacer(modifier = Modifier.height(200.dp))
                Text(
                    text = stringResource(R.string.find_category),
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                )
                when (uiState.categoryState) {
                    is CategoriesUiState.Error -> {
                        Box(Modifier.fillMaxWidth()) {
                            Text(text = "Error")
                        }
                    }
                    is CategoriesUiState.Loading -> {
                        ListCategory(
                            categories = dummyCategories,
                            isLoading = true,
                            selectedCategory = uiState.selectedCategory,
                            onCategorySelected = { },
                        )
                    }
                    is CategoriesUiState.Success -> {
                        ListCategory(
                            categories = uiState.categoryState.categories,
                            isLoading = false,
                            selectedCategory = uiState.selectedCategory,
                            onCategorySelected = onCategorySelected
                        )
                    }
                }
            }
        }

        when (uiState.productsByCategoryState) {
            is ProductByCategoryUiState.Loading -> {
                ListProductLoadingScreen()
            }
            is ProductByCategoryUiState.Loaded -> {
                ListProduct(
                    products = products,
                    onProductClick = onProductClick,
                    modifier = Modifier.testTag(TestTag.PRODUCT_LIST_SECTION),
                )
            }
        }
    }
}

@Composable
fun ListCategory(
    categories: List<Category>,
    isLoading: Boolean,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .padding(start = 8.dp)
            .height(44.dp)
            .testTag(TestTag.CATEGORY_SECTION)
    ) {
        items(
            items = categories,
            key = { it.id }
        ) { category ->
            Button(
                onClick = { onCategorySelected(category) },
                modifier = Modifier
                    .height(44.dp)
                    .padding(horizontal = 8.dp)
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.shimmer(),
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = if (category.id != selectedCategory.id) buttonColors(
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
                    tint = if (category.id != selectedCategory.id) Color.Black else Color.White,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = category.name, style = MaterialTheme.typography.button)
            }
        }
    }
}

val dummyCategories: List<Category> = List(10) {
    Category(it, "elektronik")
}
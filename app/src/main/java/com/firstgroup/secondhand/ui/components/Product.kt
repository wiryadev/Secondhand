package com.firstgroup.secondhand.ui.components

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.model.Product
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

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
            AsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(height = 100.dp)
                    .fillMaxWidth()
                    .padding(all = 4.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.shimmer(),
                    )
            )
            Text(
                text = product.name,
                style = MaterialTheme.typography.body1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
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

@Composable
fun ErrorCard(
    message: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colors.error),
        modifier = modifier
            .size(width = 156.dp, height = 206.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.error
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onClick) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}

@Composable
fun ListProduct(
    products: List<Product>,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
    ) {
        items(
            items = products,
            key = { it.id }
        ) { product ->
            ProductItem(
                product = product,
                isLoading = false,
                onClick = onProductClick,
            )
        }
    }
}

@Composable
fun ListProduct(
    products: LazyPagingItems<Product>,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val errorState = products.loadState.refresh as? LoadState.Error
        ?: products.loadState.append as? LoadState.Error

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
    ) {
        items(
            items = products,
        ) { product ->
            product?.let {
                ProductItem(
                    product = it,
                    isLoading = false,
                    onClick = onProductClick,
                )
            }
        }

        products.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    items(10) {
                        ProductItem(
                            product = dummyProduct,
                            isLoading = true,
                            onClick = {},
                        )
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        ProductItem(
                            product = dummyProduct,
                            isLoading = true,
                            onClick = {},
                        )
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    item {
                        GenericErrorScreen(
                            message = errorState?.error?.message.toString(),
                            modifier = Modifier.fillMaxSize(),
                            onClick = ::retry,
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    item {
                        ErrorCard(
                            message = errorState?.error?.message.toString(),
                            onClick = ::retry,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ListProductLoadingScreen(
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
    ) {
        items(10) {
            ProductItem(
                product = dummyProduct,
                isLoading = true,
                onClick = {},
            )
        }
    }
}

fun <T : Any> LazyGridScope.items(
    items: LazyPagingItems<T>,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable LazyGridItemScope.(value: T?) -> Unit
) {
    items(
        count = items.itemCount,
        key = if (key == null) null else { index ->
            val item = items.peek(index)
            if (item == null) {
                GridPagingPlaceholderKey(index)
            } else {
                key(item)
            }
        }
    ) { index ->
        itemContent(items[index])
    }
}

private data class GridPagingPlaceholderKey(private val index: Int) : Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(index)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @Suppress("unused")
        @JvmField
        val CREATOR: Parcelable.Creator<GridPagingPlaceholderKey> =
            object : Parcelable.Creator<GridPagingPlaceholderKey> {
                override fun createFromParcel(parcel: Parcel) =
                    GridPagingPlaceholderKey(parcel.readInt())

                override fun newArray(size: Int) = arrayOfNulls<GridPagingPlaceholderKey?>(size)
            }
    }
}


val dummyProduct = Product(
    1,
    "lenovo",
    "ww",
    50000,
    "https://firebasestorage.googleapis.com/v0/b/market-final-project.appspot.com/o/banner%2FBAN-1655129268343-gundam00.jpg?alt=media",
    "bekasi",
    2,
    ""
)
package com.firstgroup.secondhand.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.model.Wishlist

@Composable
fun WishlistProduct(
    wishlist: List<Wishlist>,
    onWishlistClick: (Int) -> Unit,
    onRemoveWishlistClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
    ) {
        items(
            items = wishlist,
            key = { it.id },
        ) {
            Column(modifier = Modifier.clickable { onWishlistClick(it.product.id) }) {
                if (it.id != wishlist[0].id) {
                    Divider(color = Color.Gray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    // image product
                    val painterProductImage = rememberAsyncImagePainter(
                        model = it.product.imageUrl
                    )
                    Image(
                        painter = painterProductImage,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    // product detail : name, price, bid
                    Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = it.product.name,
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        // product normal price
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Rp ${it.product.price}",
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.LineThrough
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    IconButton(
                        onClick = { onRemoveWishlistClick(it.id) },
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_wishlist),
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }
    }
}
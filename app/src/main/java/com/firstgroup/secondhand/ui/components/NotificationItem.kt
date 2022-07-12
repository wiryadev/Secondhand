package com.firstgroup.secondhand.ui.components

import android.text.format.DateFormat.format
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.core.model.Notification
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_INSTANT

@Composable
fun NotificationList(
    notifications: List<Notification>
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        itemsIndexed(
            items = notifications
        ) { index, notification ->
            NotificationItem(notification = notification)
            if (index < notifications.lastIndex) {
                Divider(
                    color = MaterialTheme.colors.onSecondary,
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
fun NotificationItem(
    notification: Notification,
) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            // image product
            val painterProductImage = rememberAsyncImagePainter(
                model = notification.imageUrl
            )
            val isImageLoading = painterProductImage.state is AsyncImagePainter.State.Loading
            Image(
                painter = painterProductImage,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .placeholder(
                        visible = isImageLoading,
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )
            Spacer(modifier = Modifier.width(16.dp))
            // product detail : name, price, bid
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    val listSubtitle = when (notification.status) {
                        "create" ->  "Product published successfully!"
                        "bid" ->  "Bid offer sent!"
                        "declined" -> "${notification.sellerName} is declined your bid offer :("
                        "accepted" -> "${notification.sellerName} is accepting your bid, yeay!"
                        else -> "No title"
                    }
                    Text(
                        text = listSubtitle,
                        modifier = Modifier.fillMaxWidth(0.7f),
                        style = MaterialTheme.typography.body2.copy(
                            color = Color.Gray
                        )
                    )
                    if (notification.date != "") {
                        val transactionDateFormat = DateTimeFormatter.ofPattern("dd MMM, HH:mm")
                        val convertedDate = OffsetDateTime.parse(notification.date).format(transactionDateFormat)
                        Text(
                            text = convertedDate,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.body2.copy(
                                color = Color.Gray
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.product.name,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                // product normal price
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rp ${notification.product.price}",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                // buyer bid for selected product
                Spacer(modifier = Modifier.height(4.dp))
                if (notification.bidPrice != 0) {
                    Text(
                        text = "Ditawar Rp ${notification.bidPrice}",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
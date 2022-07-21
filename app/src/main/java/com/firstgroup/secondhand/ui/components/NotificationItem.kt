package com.firstgroup.secondhand.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.model.Notification
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Composable
fun NotificationList(
    notifications: List<Notification>,
    onNotificationClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        items(
            items = notifications,
            key = { notificationItem -> notificationItem.id }
        ) { notification ->
            NotificationItem(
                notification = notification
            ) { onNotificationClick(notification.id) }
            Divider(
                color = MaterialTheme.colors.onSecondary,
                thickness = 1.dp
            )
        }
    }
}

@Composable
fun NotificationItem(
    notification: Notification,
    onNotificationClick: (Int) -> Unit
) {
    Column(modifier = Modifier.clickable { onNotificationClick(notification.id) }) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            // image product
            val painterProductImage = rememberAsyncImagePainter(
                model = notification.imageUrl
            )
            val isImageLoading = painterProductImage.state is AsyncImagePainter.State.Loading
            if (!notification.read) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_alert),
                    contentDescription = "notification marked as unread",
                    tint = MaterialTheme.colors.error
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
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
                        "create" -> "Product published successfully!"
                        "bid" -> "Bid offer sent!"
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
                    if (notification.date.isNotEmpty()) {
                        Text(
                            text = "Ordered on : ${dateFormatter(notification.date)}",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.body2.copy(
                                color = Color.Gray
                            )
                        )
                    } else {
                        Text(
                            text = "Published on : ${dateFormatter(notification.dateCreated)}",
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
                        text = "Bidded Rp ${notification.bidPrice}",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun NotificationDetails(
    notificationDetails: Notification,
    resetStateOnDialogDismiss: (Boolean) -> Unit
) {
    var dialogState by remember { mutableStateOf(true) }
    if (dialogState) {
        Dialog(
            onDismissRequest = {
                dialogState = false
                resetStateOnDialogDismiss(!dialogState)
            },
        ) {
            Column(
                modifier = Modifier.size(320.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .widthIn(min = 64.dp, max = 160.dp)
                        .heightIn(min = 48.dp, max = 128.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 4.dp,
                ) {
                    val notificationDetailImage = rememberAsyncImagePainter(
                        model = notificationDetails.imageUrl
                    )
                    val imagePainterLoading =
                        notificationDetailImage.state is AsyncImagePainter.State.Loading
                    Image(
                        painter = notificationDetailImage,
                        contentDescription = "Product Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .placeholder(
                                visible = imagePainterLoading,
                                highlight = PlaceholderHighlight.shimmer()
                            ),
                        contentScale = ContentScale.FillBounds
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 4.dp,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                    ) {
                        // text product name
                        Text(
                            text = "Product : ${notificationDetails.product.name}",
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .padding(
                                    top = 16.dp,
                                )
                        )
                        // text product seller
                        val listSubtitle = when (notificationDetails.status) {
                            "create" -> "Product published successfully!"
                            "bid" -> "Bid offer sent!"
                            "declined" -> "${notificationDetails.sellerName} is declined your bid offer :("
                            "accepted" -> "${notificationDetails.sellerName} is accepting your bid, yeay!"
                            else -> "No title"
                        }
                        Text(
                            text = listSubtitle,
                            style = MaterialTheme.typography.body2.copy(
                                color = Color.Gray
                            ),
                            modifier = Modifier
                                .padding(
                                    top = 4.dp,
                                )
                        )
                        // text product original price
                        Text(
                            text = "Product Price : Rp ${notificationDetails.product.price}",
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .padding(
                                    top = 8.dp,
                                )
                        )
                        if (notificationDetails.status != "create") {
                            // text buyer bid price
                            Text(
                                text = "Your offer : Rp ${notificationDetails.bidPrice}",
                                style = MaterialTheme.typography.body1.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .padding(
                                        top = 8.dp,
                                        bottom = 16.dp
                                    )
                            )
                            if (notificationDetails.date != "") {
                                Text(
                                    text = "Ordered on : ${dateFormatter(notificationDetails.date)}",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.End,
                                    style = MaterialTheme.typography.body2.copy(
                                        color = Color.Gray
                                    )
                                )
                            }
                        } else {
                            Text(
                                text = "Published on : ${dateFormatter(notificationDetails.dateCreated)}",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.body2.copy(
                                    color = Color.Gray
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
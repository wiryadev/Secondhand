package com.firstgroup.secondhand.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.model.Order
import com.firstgroup.secondhand.domain.order.UpdateOrderUseCase
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun ListOrders(
    orders: List<Order>,
    onOrderClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
    ) {
        items(
            items = orders,
            key = { it.id },
        ) { order ->
            Column(modifier = Modifier.clickable { onOrderClick(order.id) }) {
                if (order.id != orders[0].id) {
                    Divider(color = Color.Gray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // image product
                    val painterProductImage = rememberAsyncImagePainter(
                        model = order.product.imageUrl
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
                            if (order.transactionDate.isNotEmpty()) {
                                Text(
                                    text = stringResource(
                                        id = R.string.buyer_order_detail_date_and_status,
                                        dateFormatter(order.transactionDate),
                                        orderStatus(order.status)
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.End,
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = order.product.name,
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        // product normal price
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Rp ${order.product.price}",
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.LineThrough
                            )
                        )
                        // buyer bid for selected product
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Ditawar Rp ${order.bidPrice}",
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

@Composable
fun OrderDetails(
    orderData: Order,
    resetAlertDialogState: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    updateBidPrice: (UpdateOrderUseCase.Param) -> Unit
) {
    var isDialogOpen by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(true) }

    if (isDialogOpen) {
        var isTextFieldVisible by remember { mutableStateOf(false) }
        var newBidPrice by remember { mutableStateOf("${orderData.bidPrice}") }
        AlertDialog(
            onDismissRequest = {},
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(64.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.buyer_order_detail_title,
                            orderData.id.toString()
                        ),
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .placeholder(
                                visible = isLoading,
                                highlight = PlaceholderHighlight.shimmer()
                            )
                    )
                    if (orderData.transactionDate.isNotEmpty()) {
                        Text(
                            text = stringResource(
                                id = R.string.buyer_order_detail_date_and_status,
                                dateFormatter(orderData.transactionDate),
                                orderData.status
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .placeholder(
                                    visible = isLoading,
                                    highlight = PlaceholderHighlight.shimmer()
                                ),
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.body2
                        )
                    } else {
                        Text(
                            text = stringResource(
                                id = R.string.error_buyer_order_detail_date_and_status,
                                "No Transaction date",
                                orderData.status
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .placeholder(
                                    visible = isLoading,
                                    highlight = PlaceholderHighlight.shimmer()
                                ),
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                        .wrapContentSize(),
                ) {
                    // Product Name
                    Text(
                        text = orderData.product.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .placeholder(
                                visible = isLoading,
                                highlight = PlaceholderHighlight.shimmer()
                            ),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.body2.copy(
                            fontSize = 18.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Product Image and Description
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = 4.dp,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            val notificationDetailImage = rememberAsyncImagePainter(
                                model = orderData.product.imageUrl
                            )
                            val imagePainterLoading =
                                notificationDetailImage.state is AsyncImagePainter.State.Loading
                            // Product Image
                            Image(
                                painter = notificationDetailImage,
                                contentDescription = "Product Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                                    .weight(1f)
                                    .placeholder(
                                        visible = isLoading || imagePainterLoading,
                                        highlight = PlaceholderHighlight.shimmer()
                                    ),
                                contentScale = ContentScale.FillBounds
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            // Product Description
                            Text(
                                text = orderData.product.description,
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    // Order Bid Prices
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                    ) {
                        this@Column.AnimatedVisibility(
                            visible = !isTextFieldVisible,
                            enter = expandHorizontally(),
                            exit = shrinkHorizontally()
                        ) {
                            Text(
                                text = "Current Bid Price : ${orderData.bidPrice}",
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.body1,
                            )
                        }
                        this@Column.AnimatedVisibility(
                            visible = isTextFieldVisible,
                            enter = expandHorizontally(),
                            exit = shrinkHorizontally()
                        ) {
                            OutlinedTextField(
                                value = newBidPrice,
                                onValueChange = { newBidPrice = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = colorResource(id = R.color.neutral_02),
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                textStyle = MaterialTheme.typography.body1,
                                shape = RoundedCornerShape(16.dp)
                            )
                        }
                    }
                }
            },
            buttons = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        AnimatedVisibility(
                            visible = !isTextFieldVisible,
                            enter = expandHorizontally(),
                            exit = shrinkHorizontally()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                PrimaryButton(
                                    onClick = { isTextFieldVisible = true },
                                    content = {
                                        Text(text = "Update Bid")
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                SecondaryButton(
                                    onClick = {
                                        isDialogOpen = false
                                        resetAlertDialogState(!isDialogOpen)
                                    },
                                    content = {
                                        Text(text = "Close")
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = isTextFieldVisible,
                            enter = expandHorizontally(),
                            exit = shrinkHorizontally()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                SecondaryButton(
                                    onClick = { isTextFieldVisible = false },
                                    content = {
                                        Text(text = "Cancel")
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                // update parameter
                                val updateParam = UpdateOrderUseCase.Param(
                                    orderId = orderData.id,
                                    newBidPrice = newBidPrice.toInt()
                                )
                                PrimaryButton(
                                    onClick = {
                                        /* update order usecase here */
                                        updateBidPrice(updateParam)
                                        isTextFieldVisible = false
                                    },
                                    content = {
                                        Text(text = "Update")
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            },
            modifier = modifier.wrapContentSize(),
            shape = RoundedCornerShape(16.dp),
            properties = DialogProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = false
            )
        )
    }

    if (orderData.product.seller != null) {
        isLoading = false
    }
}
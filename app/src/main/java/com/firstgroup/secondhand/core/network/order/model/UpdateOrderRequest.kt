package com.firstgroup.secondhand.core.network.order.model

data class UpdateOrderRequest(
    val orderId: Int,
    val bidPrice: Int,
)

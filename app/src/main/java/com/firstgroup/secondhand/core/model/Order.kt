package com.firstgroup.secondhand.core.model

data class Order(
    val id: Int,
    val bidPrice: Int,
    val status: String,
    val product: Product,
    val transactionDate: String,
)

data class CreateOrder(
    val id: Int,
    val price: Int,
    val status: String,
    val date: String,
)

data class RespondOrder(
    val id: Int,
    val productId: Int,
    val productName: String,
    val buyerId: Int,
    val price: Int,
    val status: String,
)
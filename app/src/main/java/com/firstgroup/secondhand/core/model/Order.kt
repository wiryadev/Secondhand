package com.firstgroup.secondhand.core.model

data class Order(
    val id: Int,
    val bidPrice: Int,
    val status: String,
    val product: Product,
)

data class CreateOrder(
    val id: Int,
    val price: Int,
    val status: String,
    val date: String,
)
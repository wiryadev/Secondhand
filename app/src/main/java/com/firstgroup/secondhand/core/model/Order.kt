package com.firstgroup.secondhand.core.model

data class Order(
    val id: Int,
    val bidPrice: Int,
    val status: String,
    val productName: String,
    val productImage: String,
    val productPrice: Int,

    // enable when api fixed
//    val sellerName: String,
//    val sellerCity: String,
)

data class CreateOrder(
    val id: Int,
    val price: Int,
    val status: String,
    val date: String,
)
package com.firstgroup.secondhand.core.model

data class Notification(
    val id: Int,
    val read: Boolean,
    val bidPrice: Int,
    val product: Product,
    val status: String,
    val buyerName: String,
    val sellerName: String,
    val imageUrl: String,
    val date: String,
    val dateCreated: String
) {
    data class Product(
        val id: Int,
        val name: String,
        val price: Int,
    )
}

data class UpdateNotification(
    val id: Int,
    val read: Boolean,
)

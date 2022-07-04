package com.firstgroup.secondhand.core.model

data class Notification(
    val id: Int,
    val read: Boolean,
    val bidPrice: Int,
    val product: Product,
    val date: String,
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

package com.firstgroup.secondhand.core.model

data class Product(
    val id: Int,
    val name: String,
    val description: String?,
    val price: Int,
    val imageUrl: String,
    val location: String?,
    val userId: Int,
    val category: String,
    val seller: Seller? = null,
) {
    data class Seller(
        val id: Int,
        val name: String,
        val imageUrl: String?,
        val city: String,
    )
}

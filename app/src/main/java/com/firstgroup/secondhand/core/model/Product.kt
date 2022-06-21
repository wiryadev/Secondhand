package com.firstgroup.secondhand.core.model

data class Product(
    val id: Int,
    val name: String,
    val description: String?,
    val price: Int,
    val imageUrl: String?,
    val location: String?,
    val userId: Int,
    val status: String?,
    val category: String,
)

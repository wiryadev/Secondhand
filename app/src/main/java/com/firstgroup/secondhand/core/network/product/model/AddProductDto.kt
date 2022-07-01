package com.firstgroup.secondhand.core.network.product.model


import com.firstgroup.secondhand.core.model.Product
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddProductDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "base_price")
    val basePrice: Int,
    @Json(name = "location")
    val location: String,
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "image_name")
    val imageName: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "updatedAt")
    val updatedAt: String,
) {
    fun mapToDomainModel() = Product(
        id = id,
        name = name,
        description = description,
        price = basePrice,
        imageUrl = imageUrl,
        location = location,
        userId = userId,
        category = "",
    )
}
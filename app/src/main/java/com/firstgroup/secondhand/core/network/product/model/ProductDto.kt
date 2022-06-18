package com.firstgroup.secondhand.core.network.product.model


import com.firstgroup.secondhand.core.model.Product
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDto(
    @Json(name = "base_price")
    val basePrice: Int,
    @Json(name = "Categories")
    val categories: List<CategoryDto>,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image_name")
    val imageName: String,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "location")
    val location: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "updatedAt")
    val updatedAt: String,
    @Json(name = "user_id")
    val userId: Int,
) {
    fun mapToDomainModel() = Product(
        id = id,
        name = name,
        description = description,
        price = basePrice,
        imageUrl = imageUrl,
        location = location,
        userId = userId,
        status = status,
        category = categories[0].name,
    )
}
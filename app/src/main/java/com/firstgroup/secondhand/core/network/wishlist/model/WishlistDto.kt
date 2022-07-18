package com.firstgroup.secondhand.core.network.wishlist.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WishlistDto(
    @Json(name = "createdAt")
    val createdAt: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "Product")
    val product: Product?,
    @Json(name = "product_id")
    val productId: Int?,
    @Json(name = "updatedAt")
    val updatedAt: String?,
    @Json(name = "user_id")
    val userId: Int?
) {
    @JsonClass(generateAdapter = true)
    data class Product(
        @Json(name = "base_price")
        val basePrice: Int?,
        @Json(name = "createdAt")
        val createdAt: String?,
        @Json(name = "description")
        val description: String?,
        @Json(name = "id")
        val id: Int?,
        @Json(name = "image_name")
        val imageName: String?,
        @Json(name = "image_url")
        val imageUrl: String?,
        @Json(name = "location")
        val location: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "status")
        val status: String?,
        @Json(name = "updatedAt")
        val updatedAt: String?,
        @Json(name = "user_id")
        val userId: Int?
    )
}
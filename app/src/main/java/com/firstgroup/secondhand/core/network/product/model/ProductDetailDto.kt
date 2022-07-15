package com.firstgroup.secondhand.core.network.product.model


import com.firstgroup.secondhand.core.model.Product
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDetailDto(
    @Json(name = "base_price")
    val basePrice: Int?,
    @Json(name = "Categories")
    val categories: List<Category>,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "description")
    val description: String?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "location")
    val location: String,
    @Json(name = "name")
    val name: String?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "updatedAt")
    val updatedAt: String,
    @Json(name = "User")
    val user: User,
    @Json(name = "user_id")
    val userId: Int
) {
    @JsonClass(generateAdapter = true)
    data class Category(
        @Json(name = "id")
        val id: Int,
        @Json(name = "name")
        val name: String
    )

    @JsonClass(generateAdapter = true)
    data class User(
        @Json(name = "address")
        val address: String,
        @Json(name = "city")
        val city: String?,
        @Json(name = "email")
        val email: String,
        @Json(name = "full_name")
        val fullName: String,
        @Json(name = "id")
        val id: Int,
        @Json(name = "image_url")
        val imageUrl: String?,
        @Json(name = "phone_number")
        val phoneNumber: String
    )

    fun mapToDomainModel() = Product(
        id = id,
        name = name.orEmpty(),
        description = description.orEmpty(),
        price = basePrice ?: 0,
        imageUrl = imageUrl.orEmpty(),
        location = location,
        userId = userId,
        category = try {
            val categories = categories.map { it.name }
            categories.joinToString(separator = ", ")
        } catch (e: Exception) {
            "No Categories"
        },
        seller = Product.Seller(
            id = this.user.id,
            name = this.user.fullName,
            imageUrl = this.user.imageUrl,
            city = this.user.city ?: "",
        )
    )
}
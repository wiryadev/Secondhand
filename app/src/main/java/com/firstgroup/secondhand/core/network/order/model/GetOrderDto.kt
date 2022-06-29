package com.firstgroup.secondhand.core.network.order.model


import com.firstgroup.secondhand.core.model.Order
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetOrderDto(
    @Json(name = "buyer_id")
    val buyerId: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "price")
    val price: Int,
    @Json(name = "Product")
    val product: Product,
    @Json(name = "product_id")
    val productId: Int,
    @Json(name = "status")
    val status: String,
    @Json(name = "User")
    val user: User,
) {
    @JsonClass(generateAdapter = true)
    data class Product(
        @Json(name = "base_price")
        val basePrice: Int,
        @Json(name = "description")
        val description: String,
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
        @Json(name = "user_id")
        val userId: Int,
    )

    @JsonClass(generateAdapter = true)
    data class User(
        @Json(name = "address")
        val address: String,
        @Json(name = "city")
        val city: Any,
        @Json(name = "email")
        val email: String,
        @Json(name = "full_name")
        val fullName: String,
        @Json(name = "phone_number")
        val phoneNumber: String,
    )

    fun mapToDomain() = Order(
        id = id,
        bidPrice = price,
        status = status,
        productName = this.product.name,
        productImage = this.product.imageUrl,
        productPrice = this.product.basePrice,
    )
}
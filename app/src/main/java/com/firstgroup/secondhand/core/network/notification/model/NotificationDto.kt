package com.firstgroup.secondhand.core.network.notification.model


import com.firstgroup.secondhand.core.model.Notification
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NotificationDto(
    @Json(name = "base_price")
    val basePrice: Any?,
    @Json(name = "bid_price")
    val bidPrice: Int,
    @Json(name = "buyer_name")
    val buyerName: String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image_url")
    val imageUrl: Any?,
    @Json(name = "Product")
    val product: Product,
    @Json(name = "product_id")
    val productId: Int,
    @Json(name = "product_name")
    val productName: Any?,
    @Json(name = "read")
    val read: Boolean,
    @Json(name = "receiver_id")
    val receiverId: Int,
    @Json(name = "seller_name")
    val sellerName: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "transaction_date")
    val transactionDate: String,
    @Json(name = "updatedAt")
    val updatedAt: String,
    @Json(name = "User")
    val user: User
) {
    @JsonClass(generateAdapter = true)
    data class Product(
        @Json(name = "base_price")
        val basePrice: Int,
        @Json(name = "createdAt")
        val createdAt: String,
        @Json(name = "description")
        val description: String,
        @Json(name = "id")
        val id: Int,
        @Json(name = "image_name")
        val imageName: String?,
        @Json(name = "image_url")
        val imageUrl: String?,
        @Json(name = "location")
        val location: String,
        @Json(name = "name")
        val name: String?,
        @Json(name = "status")
        val status: String,
        @Json(name = "updatedAt")
        val updatedAt: String,
        @Json(name = "user_id")
        val userId: Int
    )

    @JsonClass(generateAdapter = true)
    data class User(
        @Json(name = "address")
        val address: String,
        @Json(name = "city")
        val city: String,
        @Json(name = "email")
        val email: String,
        @Json(name = "full_name")
        val fullName: String,
        @Json(name = "id")
        val id: Int,
        @Json(name = "image_url")
        val imageUrl: String,
        @Json(name = "phone_number")
        val phoneNumber: String
    )

    fun mapToDomainModel() = Notification(
        id = id,
        read = read,
        bidPrice = bidPrice,
        product = Notification.Product(
            id = this.product.id,
            name = this.product.name ?: "",
            price = this.product.basePrice,
        ),
        date = transactionDate,
    )

}
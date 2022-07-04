package com.firstgroup.secondhand.core.network.notification.model


import com.firstgroup.secondhand.core.model.UpdateNotification
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateNotificationDto(
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
    val updatedAt: String
) {
    fun mapToDomainModel() = UpdateNotification(
        id = id,
        read = read,
    )
}
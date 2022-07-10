package com.firstgroup.secondhand.core.network.order.model


import com.firstgroup.secondhand.core.model.RespondOrder
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RespondOrderDto(
    @Json(name = "base_price")
    val basePrice: String?,
    @Json(name = "buyer_id")
    val buyerId: Int,
    @Json(name = "createdAt")
    val createdAt: String?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image_product")
    val imageProduct: String?,
    @Json(name = "price")
    val price: Int?,
    @Json(name = "product_id")
    val productId: Int,
    @Json(name = "product_name")
    val productName: String?,
    @Json(name = "status")
    val status: String,
    @Json(name = "transaction_date")
    val transactionDate: String?,
    @Json(name = "updatedAt")
    val updatedAt: String?,
) {
    fun mapToDomainModel() = RespondOrder(
        id = id,
        productId = productId,
        productName = productName.orEmpty(),
        buyerId = buyerId,
        price = price ?: 0,
        status = status,
    )
}
package com.firstgroup.secondhand.core.network.order.model


import com.firstgroup.secondhand.core.model.CreateOrder
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateOrderDto(
    @Json(name = "buyer_id")
    val buyerId: Int,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "price")
    val price: Int,
    @Json(name = "product_id")
    val productId: Int,
    @Json(name = "status")
    val status: String,
    @Json(name = "updatedAt")
    val updatedAt: String
) {
    fun mapToDomain() = CreateOrder(
        id = id,
        price = price,
        status = status,
        date = createdAt,
    )
}
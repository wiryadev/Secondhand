package com.firstgroup.secondhand.core.network.history.model


import com.firstgroup.secondhand.core.model.History
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HistoryDto(
    @Json(name = "category")
    val category: String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "price")
    val price: Int,
    @Json(name = "product_name")
    val productName: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "transaction_date")
    val transactionDate: String,
    @Json(name = "updatedAt")
    val updatedAt: String,
    @Json(name = "user_id")
    val userId: Int
) {
    fun mapToDomainModel() = History(
        id = id,
        productName = productName,
        imageUrl = imageUrl.orEmpty(),
        category = category,
        date = transactionDate,
    )
}
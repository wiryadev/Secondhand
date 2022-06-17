package com.firstgroup.secondhand.core.network.product.model


import com.firstgroup.secondhand.core.model.Banner
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BannerDto(
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image_name")
    val imageName: String,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "updatedAt")
    val updatedAt: String,
    @Json(name = "user_id")
    val userId: Int
) {
    fun mapToDomainModel() = Banner(
        id = id,
        name = name,
        imageUrl = imageUrl,
        userId = userId,
    )
}
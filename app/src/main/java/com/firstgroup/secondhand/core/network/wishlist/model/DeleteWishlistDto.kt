package com.firstgroup.secondhand.core.network.wishlist.model


import com.firstgroup.secondhand.core.model.BasicResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeleteWishlistDto(
    @Json(name = "message")
    val message: String,
    @Json(name = "name")
    val name: String
) {
    fun mapToDomainModel() = BasicResponse(
        name = name,
        message = message,
    )
}
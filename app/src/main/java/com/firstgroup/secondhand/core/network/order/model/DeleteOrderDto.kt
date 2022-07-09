package com.firstgroup.secondhand.core.network.order.model


import com.firstgroup.secondhand.core.model.BasicResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeleteOrderDto(
    @Json(name = "message")
    val message: String,
    @Json(name = "name")
    val name: String,
) {
    fun mapToDomainModel() = BasicResponse(
        name = name,
        message = message,
    )
}
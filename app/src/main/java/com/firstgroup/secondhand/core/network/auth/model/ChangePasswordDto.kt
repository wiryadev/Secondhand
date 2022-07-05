package com.firstgroup.secondhand.core.network.auth.model


import com.firstgroup.secondhand.core.model.ChangePassword
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChangePasswordDto(
    @Json(name = "message")
    val message: String,
    @Json(name = "name")
    val name: String
) {
    fun mapToDomainModel() = ChangePassword(
        name = name,
        message = message,
    )
}
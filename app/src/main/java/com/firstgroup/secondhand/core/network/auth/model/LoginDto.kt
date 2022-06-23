package com.firstgroup.secondhand.core.network.auth.model

import com.firstgroup.secondhand.core.model.Authentication
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Network representation of Login
 */
@JsonClass(generateAdapter = true)
data class LoginDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "access_token")
    val accessToken: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "name")
    val name: String
) {
    fun mapToDomain() = Authentication(
        id = id,
        email = email,
        name = name,
        token = accessToken,
    )
}
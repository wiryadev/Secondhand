package com.firstgroup.secondhand.core.network.auth.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Network representation of Login
 */
@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "access_token")
    val accessToken: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "name")
    val name: String
)
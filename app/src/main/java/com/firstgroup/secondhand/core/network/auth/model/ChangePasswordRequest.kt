package com.firstgroup.secondhand.core.network.auth.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChangePasswordRequest(
    @Json(name = "current_password")
    val currentPassword: String,
    @Json(name = "new_password")
    val newPassword: String,
    @Json(name = "confirm_password")
    val confirmationPassword: String,
)

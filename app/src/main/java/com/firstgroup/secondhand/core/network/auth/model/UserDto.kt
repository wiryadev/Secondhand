package com.firstgroup.secondhand.core.network.auth.model

import com.firstgroup.secondhand.core.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "full_name")
    val fullName: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "phone_number")
    val phoneNumber: String,
    @Json(name = "address")
    val address: String,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "updatedAt")
    val updatedAt: String,
) {
    fun mapToDomain() = User(
        fullName = fullName,
        email = email,
        password = password,
        phoneNo = phoneNumber,
        address = address,
        profilePicture = imageUrl,
    )
}
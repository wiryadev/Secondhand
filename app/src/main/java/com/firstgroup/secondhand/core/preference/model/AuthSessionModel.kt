package com.firstgroup.secondhand.core.preference.model

import com.firstgroup.secondhand.core.model.Authentication

data class AuthSessionModel(
    val id: Int,
    val token: String,
    val email: String,
    val name: String,
) {
    fun mapToDomain() = Authentication(
        id = id,
        email = email,
        name = name,
        token = token,
    )
}

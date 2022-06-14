package com.firstgroup.secondhand.core.preference.model

import com.firstgroup.secondhand.core.model.Authentication

data class AuthSessionModel(
    val token: String,
    val email: String,
    val name: String,
) {
    fun mapToDomain() = Authentication(
        email = email,
        name = name,
        token = token,
    )
}

package com.firstgroup.secondhand.core.network.auth.model

data class LoginRequest(
    val email: String,
    val password: String,
)

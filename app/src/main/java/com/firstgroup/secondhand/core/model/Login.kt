package com.firstgroup.secondhand.core.model

/**
 * External data layer representation of a Login
 */
data class Login(
    val email: String,
    val name: String,
    val token: String,
)

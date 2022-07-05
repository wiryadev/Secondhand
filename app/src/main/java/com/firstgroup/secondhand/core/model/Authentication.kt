package com.firstgroup.secondhand.core.model

/**
 * External data layer representation of Authentication
 */
data class Authentication(
    val id: Int,
    val email: String,
    val name: String,
    val token: String,
)

data class ChangePassword(
    val name: String,
    val message: String,
)

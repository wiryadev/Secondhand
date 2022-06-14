package com.firstgroup.secondhand.core.model

/**
 * External data layer representation of Authentication
 */
data class Authentication(
    val email: String,
    val name: String,
    val token: String,
)

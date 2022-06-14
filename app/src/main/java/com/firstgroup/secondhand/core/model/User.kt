package com.firstgroup.secondhand.core.model

/**
 * External data layer representation of a User
 */
data class User(
    val fullName: String,
    val email: String,
    val password: String,
    val phoneNo: String,
    val address: String,
    val profilePicture: String?,
)

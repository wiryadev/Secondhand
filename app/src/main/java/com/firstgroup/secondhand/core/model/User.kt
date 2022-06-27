package com.firstgroup.secondhand.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * External data layer representation of a User
 */
@Parcelize
data class User(
    val fullName: String,
    val email: String,
    val password: String,
    val phoneNo: String,
    val address: String,
    val profilePicture: String?,
): Parcelable

package com.firstgroup.secondhand.core.data.repositories.auth

import com.firstgroup.secondhand.core.model.Authentication
import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
import com.firstgroup.secondhand.core.network.auth.model.LoginResponse
import com.firstgroup.secondhand.core.network.auth.model.UserResponse
import com.firstgroup.secondhand.core.preference.model.AuthSessionModel

interface AuthRepository {

    suspend fun login(
        email: String,
        password: String,
    ): LoginResponse

    suspend fun register(
        authUserRequest: AuthUserRequest,
    ): UserResponse

    suspend fun getUser(
        token: String,
    ): UserResponse

    suspend fun updateUser(
        token: String,
        authUserRequest: AuthUserRequest,
    ): UserResponse

    suspend fun getUserSession(): AuthSessionModel

    suspend fun saveUserSession(user: Authentication)

    suspend fun deleteUserSession()

}
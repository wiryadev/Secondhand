package com.firstgroup.secondhand.core.network.auth

import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
import com.firstgroup.secondhand.core.network.auth.model.LoginResponse
import com.firstgroup.secondhand.core.network.auth.model.UserResponse

interface AuthRemoteDataSource {

    suspend fun login(
        email: String,
        password: String,
    ): LoginResponse

    suspend fun register(
        authUserRequest: AuthUserRequest
    ): UserResponse

    suspend fun getUser(): UserResponse

    suspend fun updateUser(
        authUserRequest: AuthUserRequest
    ): UserResponse

}
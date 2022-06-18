package com.firstgroup.secondhand.core.network.auth

import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
import com.firstgroup.secondhand.core.network.auth.model.LoginRequest
import com.firstgroup.secondhand.core.network.auth.model.LoginResponse
import com.firstgroup.secondhand.core.network.auth.model.UserResponse

interface AuthRemoteDataSource {

    suspend fun login(
        loginRequest: LoginRequest,
    ): LoginResponse

    suspend fun register(
        authUserRequest: AuthUserRequest
    ): UserResponse

    suspend fun getUser(): UserResponse

    suspend fun updateUser(
        authUserRequest: AuthUserRequest
    ): UserResponse

}
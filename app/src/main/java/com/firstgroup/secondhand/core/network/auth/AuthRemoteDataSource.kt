package com.firstgroup.secondhand.core.network.auth

import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
import com.firstgroup.secondhand.core.network.auth.model.LoginRequest
import com.firstgroup.secondhand.core.network.auth.model.LoginDto
import com.firstgroup.secondhand.core.network.auth.model.UserDto

interface AuthRemoteDataSource {

    suspend fun login(
        loginRequest: LoginRequest,
    ): LoginDto

    suspend fun register(
        authUserRequest: AuthUserRequest
    ): UserDto

    suspend fun getUser(): UserDto

    suspend fun updateUser(
        authUserRequest: AuthUserRequest
    ): UserDto

}
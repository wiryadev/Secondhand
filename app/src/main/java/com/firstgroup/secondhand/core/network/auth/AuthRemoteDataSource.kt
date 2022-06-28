package com.firstgroup.secondhand.core.network.auth

import com.firstgroup.secondhand.core.network.auth.model.*

interface AuthRemoteDataSource {

    suspend fun login(
        loginRequest: LoginRequest,
    ): LoginDto

    suspend fun register(
        registerUserRequest: RegisterUserRequest
    ): UserDto

    suspend fun getUser(): UserDto

    suspend fun updateUser(
        updateUserRequest: UpdateUserRequest
    ): UserDto

}
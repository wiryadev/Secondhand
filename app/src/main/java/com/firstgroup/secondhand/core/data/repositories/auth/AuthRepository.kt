package com.firstgroup.secondhand.core.data.repositories.auth

import com.firstgroup.secondhand.core.model.Authentication
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
import com.firstgroup.secondhand.core.network.auth.model.LoginRequest

interface AuthRepository {

    suspend fun login(
        loginRequest: LoginRequest,
    ): Authentication

    suspend fun register(
        authUserRequest: AuthUserRequest,
    ): User

    suspend fun getUser(): User

    suspend fun updateUser(
        authUserRequest: AuthUserRequest,
    ): User

    suspend fun getUserSession(): Authentication

    suspend fun saveUserSession(user: Authentication)

    suspend fun deleteUserSession()

}
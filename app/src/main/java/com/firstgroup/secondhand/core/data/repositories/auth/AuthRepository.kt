package com.firstgroup.secondhand.core.data.repositories.auth

import com.firstgroup.secondhand.core.model.Authentication
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.core.network.auth.model.RegisterUserRequest
import com.firstgroup.secondhand.core.network.auth.model.LoginRequest
import com.firstgroup.secondhand.core.network.auth.model.UpdateUserRequest
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(
        loginRequest: LoginRequest,
    ): Authentication

    suspend fun register(
        registerUserRequest: RegisterUserRequest,
    ): User

    suspend fun getUser(): User

    suspend fun updateUser(
        updateUserRequest: UpdateUserRequest,
    ): User

    fun getUserSession(): Flow<Authentication>

    suspend fun saveUserSession(user: Authentication)

    suspend fun deleteUserSession()

}
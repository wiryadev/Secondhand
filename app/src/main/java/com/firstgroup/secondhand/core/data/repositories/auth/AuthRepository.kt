package com.firstgroup.secondhand.core.data.repositories.auth

import com.firstgroup.secondhand.core.model.Login
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun login(
        email: String,
        password: String,
    ): Flow<Login>

    fun register(
        authUserRequest: AuthUserRequest,
    ): Flow<User>

}
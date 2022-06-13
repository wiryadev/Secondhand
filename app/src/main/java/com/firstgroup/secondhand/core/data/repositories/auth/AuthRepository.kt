package com.firstgroup.secondhand.core.data.repositories.auth

import com.firstgroup.secondhand.core.model.Login
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun login(
        email: String,
        password: String,
    ): Flow<Login>

}
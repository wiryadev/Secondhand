package com.firstgroup.secondhand.core.network.auth

import com.firstgroup.secondhand.core.network.auth.model.LoginResponse

interface AuthRemoteDataSource {

    suspend fun login(
        email: String,
        password: String,
    ): LoginResponse

}
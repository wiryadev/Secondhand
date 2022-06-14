package com.firstgroup.secondhand.core.network.auth

import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
import com.firstgroup.secondhand.core.network.auth.model.LoginResponse
import com.firstgroup.secondhand.core.network.auth.model.UserResponse
import com.firstgroup.secondhand.core.network.auth.retrofit.AuthService
import com.firstgroup.secondhand.core.network.utils.RequestUtil
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRemoteDataSource {

    override suspend fun login(email: String, password: String): LoginResponse {
        return authService.login(
            loginData = RequestUtil.createJsonRequestBody(
                "email" to email,
                "password" to password,
            )
        )
    }

    override suspend fun register(authUserRequest: AuthUserRequest): UserResponse {
        val formData = authUserRequest.toFormData()
        return authService.register(
            formData.requestBody,
            formData.multipart,
        )
    }

}
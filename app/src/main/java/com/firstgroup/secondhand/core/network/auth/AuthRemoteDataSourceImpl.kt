package com.firstgroup.secondhand.core.network.auth

import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
import com.firstgroup.secondhand.core.network.auth.model.LoginRequest
import com.firstgroup.secondhand.core.network.auth.model.LoginResponse
import com.firstgroup.secondhand.core.network.auth.model.UserResponse
import com.firstgroup.secondhand.core.network.auth.retrofit.AuthService
import com.firstgroup.secondhand.core.network.utils.RequestUtil
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRemoteDataSource {

    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return authService.login(
            loginData = RequestUtil.createJsonRequestBody(loginRequest)
        )
    }

    override suspend fun register(authUserRequest: AuthUserRequest): UserResponse {
        val formData = authUserRequest.toFormData()
        return authService.register(
            partMap = formData.requestBody,
            image = formData.multipart,
        )
    }

    override suspend fun getUser(): UserResponse {
        return authService.getUser()
    }

    override suspend fun updateUser(
        authUserRequest: AuthUserRequest,
    ): UserResponse {
        val formData = authUserRequest.toFormData()
        return authService.updateUser(
            partMap = formData.requestBody,
            image = formData.multipart,
        )
    }

}
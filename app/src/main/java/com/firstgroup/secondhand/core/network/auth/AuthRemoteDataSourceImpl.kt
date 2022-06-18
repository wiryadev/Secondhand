package com.firstgroup.secondhand.core.network.auth

import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
import com.firstgroup.secondhand.core.network.auth.model.LoginRequest
import com.firstgroup.secondhand.core.network.auth.model.LoginDto
import com.firstgroup.secondhand.core.network.auth.model.UserDto
import com.firstgroup.secondhand.core.network.auth.retrofit.AuthService
import com.firstgroup.secondhand.core.network.utils.RequestUtil
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRemoteDataSource {

    override suspend fun login(loginRequest: LoginRequest): LoginDto {
        return authService.login(
            loginData = RequestUtil.createJsonRequestBody(loginRequest)
        )
    }

    override suspend fun register(authUserRequest: AuthUserRequest): UserDto {
        val formData = authUserRequest.toFormData()
        return authService.register(
            partMap = formData.requestBody,
            image = formData.multipart,
        )
    }

    override suspend fun getUser(): UserDto {
        return authService.getUser()
    }

    override suspend fun updateUser(
        authUserRequest: AuthUserRequest,
    ): UserDto {
        val formData = authUserRequest.toFormData()
        return authService.updateUser(
            partMap = formData.requestBody,
            image = formData.multipart,
        )
    }

}
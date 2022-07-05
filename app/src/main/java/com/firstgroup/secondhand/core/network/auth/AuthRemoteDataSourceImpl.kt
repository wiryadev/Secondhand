package com.firstgroup.secondhand.core.network.auth

import com.firstgroup.secondhand.core.network.auth.model.*
import com.firstgroup.secondhand.core.network.auth.retrofit.AuthService
import com.firstgroup.secondhand.core.network.utils.RequestUtil
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRemoteDataSource {

    override suspend fun login(
        loginRequest: LoginRequest
    ): LoginDto = authService.login(
        loginData = RequestUtil.createJsonRequestBody(loginRequest)
    )

    override suspend fun register(registerUserRequest: RegisterUserRequest): UserDto {
        val formData = registerUserRequest.toFormData()
        return authService.register(
            partMap = formData.requestBody,
            image = formData.multipart,
        )
    }

    override suspend fun getUser(): UserDto {
        return authService.getUser()
    }

    override suspend fun updateUser(
        updateUserRequest: UpdateUserRequest,
    ): UserDto {
        val formData = updateUserRequest.toFormData()
        return authService.updateUser(
            partMap = formData.requestBody,
            image = formData.multipart,
        )
    }

    override suspend fun changePassword(
        changePasswordRequest: ChangePasswordRequest
    ): ChangePasswordDto = authService.changePassword(
        changePasswordData = RequestUtil.createJsonRequestBody(changePasswordRequest)
    )

}
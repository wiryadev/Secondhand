package com.firstgroup.secondhand.utils

import com.firstgroup.secondhand.core.model.Authentication
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.core.network.auth.model.LoginRequest
import com.firstgroup.secondhand.core.network.auth.model.RegisterUserRequest
import com.firstgroup.secondhand.domain.auth.LoginUseCase
import com.firstgroup.secondhand.domain.auth.RegisterUseCase

object AuthDummyDataProvider {

    fun provideAuthentication() = Authentication(
        id = 1,
        token = "token",
        email = "email@email.com",
        name = "name",
    )

    fun provideValidLoginParam() = LoginUseCase.Param(
        email = "email@email.com",
        password = "password",
    )

    fun provideInvalidLoginParam() = LoginUseCase.Param(
        email = "",
        password = "",
    )

    fun provideValidLoginRequest() = LoginRequest(
        email = "email@email.com",
        password = "password",
    )

    fun provideInvalidLoginRequest() = LoginRequest(
        email = "",
        password = "",
    )

    fun provideValidRegisterParam() = RegisterUseCase.Param(
        fullName = "fullName",
        email = "email@email.com",
        password = "password",
        phoneNo = "phoneNo",
        address = "address",
        city = "city",
    )

    fun provideValidRegisterRequest() = RegisterUserRequest(
        fullName = "fullName",
        email = "email@email.com",
        password = "password",
        phoneNo = "phoneNo",
        address = "address",
        city = "city",
        image = null,
    )

    fun provideValidUser() = User(
        fullName = "fullName",
        email = "email@email.com",
        password = "password",
        phoneNo = "phoneNo",
        address = "address",
        city = "city",
        profilePicture = null,
    )
}
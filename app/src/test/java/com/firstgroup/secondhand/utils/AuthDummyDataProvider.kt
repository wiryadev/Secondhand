package com.firstgroup.secondhand.utils

import com.firstgroup.secondhand.core.model.Authentication
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.core.network.auth.model.LoginRequest
import com.firstgroup.secondhand.domain.auth.LoginUseCase

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

    fun provideValidUser() = User(
        fullName = "",
        email = "",
        password = "",
        phoneNo = "",
        address = "",
        profilePicture = null,
        city = ""
    )
}
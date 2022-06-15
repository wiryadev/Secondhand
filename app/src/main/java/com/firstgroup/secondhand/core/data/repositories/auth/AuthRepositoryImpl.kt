package com.firstgroup.secondhand.core.data.repositories.auth

import com.firstgroup.secondhand.core.model.Authentication
import com.firstgroup.secondhand.core.network.auth.AuthRemoteDataSource
import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
import com.firstgroup.secondhand.core.network.auth.model.LoginResponse
import com.firstgroup.secondhand.core.network.auth.model.UserResponse
import com.firstgroup.secondhand.core.preference.AuthPreferenceDataSource
import com.firstgroup.secondhand.core.preference.model.AuthSessionModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authPreferenceDataSource: AuthPreferenceDataSource,
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): LoginResponse = authRemoteDataSource.login(email, password)

    override suspend fun register(
        authUserRequest: AuthUserRequest
    ): UserResponse = authRemoteDataSource.register(authUserRequest)


    override suspend fun getUser(
        token: String
    ): UserResponse = authRemoteDataSource.getUser(token)

    override suspend fun updateUser(
        token: String,
        authUserRequest: AuthUserRequest,
    ): UserResponse = authRemoteDataSource.updateUser(
        token = token,
        authUserRequest = authUserRequest,
    )

    override suspend fun getUserSession(): AuthSessionModel {
        return authPreferenceDataSource.getUserSession().first()
    }

    override suspend fun saveUserSession(user: Authentication) {
        val authSession = AuthSessionModel(
            token = user.token,
            email = user.email,
            name = user.name
        )
        authPreferenceDataSource.saveUserSession(user = authSession)
    }

    override suspend fun deleteUserSession() {
        authPreferenceDataSource.deleteUserSession()
    }

}

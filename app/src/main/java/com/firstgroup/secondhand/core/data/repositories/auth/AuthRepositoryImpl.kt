package com.firstgroup.secondhand.core.data.repositories.auth

import com.firstgroup.secondhand.core.model.Authentication
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.core.network.auth.AuthRemoteDataSource
import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
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
    ): Authentication = authRemoteDataSource.login(email, password).mapToDomain()

    override suspend fun register(
        authUserRequest: AuthUserRequest
    ): User = authRemoteDataSource.register(authUserRequest).mapToDomain()


    override suspend fun getUser(): User = authRemoteDataSource.getUser().mapToDomain()

    override suspend fun updateUser(
        authUserRequest: AuthUserRequest,
    ): User = authRemoteDataSource.updateUser(
        authUserRequest = authUserRequest,
    ).mapToDomain()

    override suspend fun getUserSession(): Authentication {
        return authPreferenceDataSource.getUserSession()
            .first()
            .mapToDomain()
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

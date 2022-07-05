package com.firstgroup.secondhand.core.data.repositories.auth

import com.firstgroup.secondhand.core.model.Authentication
import com.firstgroup.secondhand.core.model.ChangePassword
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.core.network.auth.AuthRemoteDataSource
import com.firstgroup.secondhand.core.network.auth.model.ChangePasswordRequest
import com.firstgroup.secondhand.core.network.auth.model.RegisterUserRequest
import com.firstgroup.secondhand.core.network.auth.model.LoginRequest
import com.firstgroup.secondhand.core.network.auth.model.UpdateUserRequest
import com.firstgroup.secondhand.core.preference.AuthPreferenceDataSource
import com.firstgroup.secondhand.core.preference.model.AuthSessionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val preferenceDataSource: AuthPreferenceDataSource,
) : AuthRepository {

    override suspend fun login(
        loginRequest: LoginRequest
    ): Authentication = remoteDataSource.login(loginRequest).mapToDomain()

    override suspend fun register(
        registerUserRequest: RegisterUserRequest
    ): User = remoteDataSource.register(registerUserRequest).mapToDomain()

    override suspend fun getUser(): User = remoteDataSource.getUser().mapToDomain()

    override suspend fun updateUser(
        updateUserRequest: UpdateUserRequest,
    ): User = remoteDataSource.updateUser(
        updateUserRequest = updateUserRequest,
    ).mapToDomain()

    override suspend fun changePassword(
        changePasswordRequest: ChangePasswordRequest
    ): ChangePassword = remoteDataSource.changePassword(changePasswordRequest).mapToDomainModel()

    override fun getUserSession(): Flow<Authentication> {
        return preferenceDataSource.getUserSession().map {
            it.mapToDomain()
        }
    }

    override suspend fun saveUserSession(user: Authentication) {
        val authSession = AuthSessionModel(
            id = user.id,
            token = user.token,
            email = user.email,
            name = user.name
        )
        preferenceDataSource.saveUserSession(user = authSession)
    }

    override suspend fun deleteUserSession() {
        preferenceDataSource.deleteUserSession()
    }

}

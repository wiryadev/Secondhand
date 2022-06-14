package com.firstgroup.secondhand.core.data.repositories.auth

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers
import com.firstgroup.secondhand.core.model.Authentication
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.core.network.auth.AuthRemoteDataSource
import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
import com.firstgroup.secondhand.core.preference.AuthPreferenceDataSource
import com.firstgroup.secondhand.core.preference.model.AuthSessionModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authPreferenceDataSource: AuthPreferenceDataSource,
    @AppDispatcher(SecondhandDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : AuthRepository {

    override fun login(
        email: String,
        password: String
    ): Flow<Authentication> = flow {
        val response = authRemoteDataSource.login(email, password)
        emit(response.mapToDomain())
    }.flowOn(ioDispatcher)

    override fun register(authUserRequest: AuthUserRequest): Flow<User> = flow {
        val response = authRemoteDataSource.register(authUserRequest)
        emit(response.mapToDomain())
    }.flowOn(ioDispatcher)

    override fun getUser(token: String): Flow<User> = flow {
        val response = authRemoteDataSource.getUser(token)
        emit(response.mapToDomain())
    }.flowOn(ioDispatcher)

    override fun updateUser(
        token: String,
        authUserRequest: AuthUserRequest,
    ): Flow<User> = flow {
        val response = authRemoteDataSource.updateUser(
            token = token,
            authUserRequest = authUserRequest,
        )
        emit(response.mapToDomain())
    }.flowOn(ioDispatcher)

    override fun getUserSession(): Flow<Authentication> {
        return authPreferenceDataSource.getUserSession()
            .map { it.mapToDomain() }
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

package com.firstgroup.secondhand.core.data.repositories.auth

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers
import com.firstgroup.secondhand.core.model.Login
import com.firstgroup.secondhand.core.network.auth.AuthRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    @AppDispatcher(SecondhandDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : AuthRepository {

    override fun login(
        email: String,
        password: String
    ): Flow<Login> = flow {
        val response = authRemoteDataSource.login(email, password)
        emit(response.mapToDomain())
    }.flowOn(ioDispatcher)

}

package com.firstgroup.secondhand.core.network.auth

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers
import com.firstgroup.secondhand.core.network.auth.model.LoginResponse
import com.firstgroup.secondhand.core.network.auth.retrofit.AuthService
import com.firstgroup.secondhand.core.network.utils.RequestUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
    @AppDispatcher(SecondhandDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : AuthRemoteDataSource {

    override suspend fun login(email: String, password: String): LoginResponse {
        return withContext(ioDispatcher) {
            authService.login(
                loginData = RequestUtil.createJsonRequestBody(
                    "email" to email,
                    "password" to password,
                )
            )
        }
    }

}
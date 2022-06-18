package com.firstgroup.secondhand.domain.auth

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.auth.AuthRepository
import com.firstgroup.secondhand.core.model.Authentication
import com.firstgroup.secondhand.core.network.auth.model.LoginRequest
import com.firstgroup.secondhand.core.network.utils.AuthInterceptor
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val authInterceptor: AuthInterceptor,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<LoginRequest, Authentication>(ioDispatcher) {

    override suspend fun execute(param: LoginRequest): Authentication {
        val login = repository.login(param)
        repository.saveUserSession(login)
        authInterceptor.setToken(login.token)
        return login
    }

}
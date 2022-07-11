package com.firstgroup.secondhand.domain.auth

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.auth.AuthRepository
import com.firstgroup.secondhand.core.model.Authentication
import com.firstgroup.secondhand.core.network.auth.model.LoginRequest
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val setTokenUseCase: SetTokenUseCase,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<LoginUseCase.Param, Authentication>(ioDispatcher) {

    override suspend fun execute(param: Param): Authentication {
        val request = LoginRequest(
            email = param.email,
            password = param.password,
        )
        val login = repository.login(request)
        repository.saveUserSession(login)
        setTokenUseCase(login.token)
        return login
    }

    data class Param(
        val email: String,
        val password: String,
    )

}
package com.firstgroup.secondhand.domain.auth

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.auth.AuthRepository
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.core.network.auth.model.RegisterUserRequest
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<RegisterUseCase.Param, User>(ioDispatcher) {

    override suspend fun execute(param: Param): User {
        val request = RegisterUserRequest(
            fullName = param.fullName,
            email = param.email,
            password = param.password,
            phoneNo = param.phoneNo,
            address = param.address,
            city = param.city,
            image = null,
        )
        return authRepository.register(request)
    }

    data class Param(
        val fullName: String,
        val email: String,
        val password: String,
        val phoneNo: String,
        val address: String,
        val city: String,
    )

}
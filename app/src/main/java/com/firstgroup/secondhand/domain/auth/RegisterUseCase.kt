package com.firstgroup.secondhand.domain.auth

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.auth.AuthRepository
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<AuthUserRequest, User>(ioDispatcher) {

    override suspend fun execute(param: AuthUserRequest): User {
        return repository.register(param).mapToDomain()
    }

}
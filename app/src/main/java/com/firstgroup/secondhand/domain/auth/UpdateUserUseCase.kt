package com.firstgroup.secondhand.domain.auth

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers
import com.firstgroup.secondhand.core.data.repositories.auth.AuthRepository
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.core.network.auth.model.UpdateUserRequest
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: AuthRepository,
    @AppDispatcher(SecondhandDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<UpdateUserRequest, User>(ioDispatcher) {

    override suspend fun execute(param: UpdateUserRequest): User {
        return repository.updateUser(param)
    }

}
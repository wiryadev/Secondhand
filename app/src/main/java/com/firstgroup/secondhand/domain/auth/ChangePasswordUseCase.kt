package com.firstgroup.secondhand.domain.auth

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.auth.AuthRepository
import com.firstgroup.secondhand.core.model.BasicResponse
import com.firstgroup.secondhand.core.network.auth.model.ChangePasswordRequest
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val repository: AuthRepository,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<ChangePasswordUseCase.Param, BasicResponse>(ioDispatcher) {

    override suspend fun execute(param: Param): BasicResponse {
        val request = ChangePasswordRequest(
            currentPassword = param.currentPassword,
            newPassword = param.newPassword,
            confirmationPassword = param.confirmationPassword,
        )
        return repository.changePassword(request)
    }

    data class Param(
        val currentPassword: String,
        val newPassword: String,
        val confirmationPassword: String,
    )

}
package com.firstgroup.secondhand.domain.auth

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers
import com.firstgroup.secondhand.core.data.repositories.auth.AuthRepository
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.core.network.auth.model.UpdateUserRequest
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import java.io.File
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: AuthRepository,
    @AppDispatcher(SecondhandDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<UpdateUserUseCase.Param, User>(ioDispatcher) {

    override suspend fun execute(param: Param): User {
        val request = UpdateUserRequest(
            fullName = param.fullName,
            phoneNo = param.phoneNo,
            address = param.address,
            city = param.city,
            image = param.image,
        )
        return repository.updateUser(request)
    }

    data class Param(
        val fullName: String,
        val phoneNo: String,
        val address: String,
        val city: String,
        val image: File? = null,
    )

}
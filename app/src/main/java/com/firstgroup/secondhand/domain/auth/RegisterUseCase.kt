package com.firstgroup.secondhand.domain.auth

import com.firstgroup.secondhand.core.common.result.asResult
import com.firstgroup.secondhand.core.data.repositories.auth.AuthRepository
import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke(authUserRequest: AuthUserRequest) =
        repository.register(authUserRequest).asResult()

}
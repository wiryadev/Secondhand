package com.firstgroup.secondhand.domain.auth

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers
import com.firstgroup.secondhand.core.data.repositories.auth.AuthRepository
import com.firstgroup.secondhand.core.model.Authentication
import com.firstgroup.secondhand.domain.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(
    private val repository: AuthRepository,
    @AppDispatcher(SecondhandDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : FlowUseCase<Any, Authentication>(ioDispatcher) {

    override suspend fun execute(param: Any): Flow<Authentication> {
        return repository.getUserSession()
    }

}
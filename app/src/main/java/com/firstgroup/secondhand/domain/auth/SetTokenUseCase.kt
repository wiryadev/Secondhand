package com.firstgroup.secondhand.domain.auth

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.network.utils.AuthInterceptor
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SetTokenUseCase @Inject constructor(
    private val authInterceptor: AuthInterceptor,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<String, Unit>(ioDispatcher) {

    override suspend fun execute(param: String) {
        authInterceptor.setToken(param)
    }

}
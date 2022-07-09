package com.firstgroup.secondhand.domain.order

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.order.OrderRepository
import com.firstgroup.secondhand.core.model.BasicResponse
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CancelOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<Int, BasicResponse>(ioDispatcher) {

    override suspend fun execute(param: Int): BasicResponse {
        return orderRepository.deleteOrderAsBuyer(param)
    }

}
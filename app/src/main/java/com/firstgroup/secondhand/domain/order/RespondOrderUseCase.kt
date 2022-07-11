package com.firstgroup.secondhand.domain.order

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.order.OrderRepository
import com.firstgroup.secondhand.core.model.RespondOrder
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RespondOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<RespondOrderUseCase.Param, RespondOrder>(ioDispatcher) {

    override suspend fun execute(param: Param): RespondOrder {
        return if (param.isAccepted) {
            orderRepository.acceptOrder(id = param.orderId)
        } else {
            orderRepository.rejectOrder(id = param.orderId)
        }
    }

    data class Param(
        val orderId: Int,
        val isAccepted: Boolean,
    )
}
package com.firstgroup.secondhand.domain.order

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.order.OrderRepository
import com.firstgroup.secondhand.core.model.Order
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetOrdersAsSellerUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<OrderFilter, List<Order>>(ioDispatcher) {

    override suspend fun execute(param: OrderFilter): List<Order> {
        return when(param) {
            OrderFilter.AllOrders -> orderRepository.getAllOrdersAsSeller()
            OrderFilter.AcceptedOrders -> orderRepository.getAcceptedOrdersAsSeller()
            OrderFilter.DeclinedOrders -> orderRepository.getDeclinedOrdersAsSeller()
            OrderFilter.PendingOrders -> orderRepository.getPendingOrdersAsSeller()
        }
    }

}

sealed interface OrderFilter {
    object AllOrders: OrderFilter
    object AcceptedOrders: OrderFilter
    object DeclinedOrders: OrderFilter
    object PendingOrders: OrderFilter
}
package com.firstgroup.secondhand.core.network.order

import com.firstgroup.secondhand.core.network.order.model.CreateOrderDto
import com.firstgroup.secondhand.core.network.order.model.DeleteOrderDto
import com.firstgroup.secondhand.core.network.order.model.GetOrderDto
import com.firstgroup.secondhand.core.network.order.model.OrderRequest
import com.firstgroup.secondhand.core.network.order.retrofit.OrderService
import com.firstgroup.secondhand.core.network.utils.RequestUtil
import javax.inject.Inject

class OrderRemoteDataSourceImpl @Inject constructor(
    private val service: OrderService
) : OrderRemoteDataSource {

    override suspend fun createOrder(
        orderRequest: OrderRequest
    ): CreateOrderDto = service.createOrder(
        orderData = RequestUtil.createJsonRequestBody(orderRequest)
    )

    override suspend fun getOrdersAsBuyer(): List<GetOrderDto> {
        return service.getOrdersAsBuyer()
    }

    override suspend fun getOrderByIdAsBuyer(id: Int): GetOrderDto {
        return service.getOrderByIdAsBuyer(id)
    }

    override suspend fun updateOrderAsBuyer(id: Int): CreateOrderDto =
        service.updateOrderAsBuyer(id)

    override suspend fun deleteOrderAsBuyer(id: Int): DeleteOrderDto =
        service.deleteOrderAsBuyer(id)

    override suspend fun getAllOrdersAsSeller(): List<GetOrderDto> {
        return service.getOrdersAsSeller()
    }

    override suspend fun getAcceptedOrdersAsSeller(): List<GetOrderDto> {
        return service.getOrdersAsSeller("accepted")
    }

    override suspend fun getDeclinedOrdersAsSeller(): List<GetOrderDto> {
        return service.getOrdersAsSeller("declined")
    }

    override suspend fun getPendingOrdersAsSeller(): List<GetOrderDto> {
        return service.getOrdersAsSeller("pending")
    }

}
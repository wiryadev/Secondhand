package com.firstgroup.secondhand.core.network.order

import com.firstgroup.secondhand.core.network.order.model.CreateOrderDto
import com.firstgroup.secondhand.core.network.order.model.GetOrderDto
import com.firstgroup.secondhand.core.network.order.model.OrderRequest
import com.firstgroup.secondhand.core.network.order.retrofit.OrderService
import com.firstgroup.secondhand.core.network.utils.RequestUtil
import javax.inject.Inject

class OrderRemoteDataSourceImpl @Inject constructor(
    private val service: OrderService
) : OrderRemoteDataSource {

    override suspend fun createOrder(orderRequest: OrderRequest): CreateOrderDto {
        return service.createOrder(
            orderData = RequestUtil.createJsonRequestBody(orderRequest)
        )
    }

    override suspend fun updateOrder(id: Int): CreateOrderDto {
        return service.updateOrder(id)
    }

    override suspend fun deleteOrder(id: Int) {
        service.deleteOrder(id)
    }

    override suspend fun getOrdersAsBuyer(): List<GetOrderDto> {
        return service.getOrdersAsBuyer()
    }

    override suspend fun getOrderByIdAsBuyer(id: Int): List<GetOrderDto> {
        return service.getOrderByIdAsBuyer(id)
    }

}
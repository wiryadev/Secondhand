package com.firstgroup.secondhand.core.network.order

import com.firstgroup.secondhand.core.network.order.model.CreateOrderDto
import com.firstgroup.secondhand.core.network.order.model.GetOrderDto
import com.firstgroup.secondhand.core.network.order.model.OrderRequest

interface OrderRemoteDataSource {

    /**
     * Buyer
     */
    suspend fun createOrder(orderRequest: OrderRequest): CreateOrderDto

    suspend fun updateOrder(id: Int): CreateOrderDto

    suspend fun deleteOrder(id: Int)

    suspend fun getOrdersAsBuyer(): List<GetOrderDto>

    suspend fun getOrderByIdAsBuyer(id: Int): List<GetOrderDto>

}
package com.firstgroup.secondhand.core.network.order

import com.firstgroup.secondhand.core.network.order.model.*

interface OrderRemoteDataSource {

    /**
     * Buyer
     */

    suspend fun createOrder(orderRequest: OrderRequest): CreateOrderDto

    suspend fun getOrdersAsBuyer(): List<GetOrderDto>

    suspend fun getOrderByIdAsBuyer(id: Int): GetOrderDto

    suspend fun updateOrder(
        orderId: Int,
        newBidPrice: Int,
    ): CreateOrderDto

    suspend fun deleteOrderAsBuyer(id: Int): DeleteOrderDto

    suspend fun getAllOrdersAsSeller(): List<GetOrderDto>

    suspend fun getAcceptedOrdersAsSeller(): List<GetOrderDto>

    suspend fun getDeclinedOrdersAsSeller(): List<GetOrderDto>

    suspend fun getPendingOrdersAsSeller(): List<GetOrderDto>

    suspend fun getOrderByIdAsSeller(id: Int): GetOrderDto

    suspend fun acceptOrder(id: Int): RespondOrderDto

    suspend fun rejectOrder(id: Int): RespondOrderDto

}
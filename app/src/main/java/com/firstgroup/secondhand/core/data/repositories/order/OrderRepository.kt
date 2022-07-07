package com.firstgroup.secondhand.core.data.repositories.order

import com.firstgroup.secondhand.core.model.CreateOrder
import com.firstgroup.secondhand.core.model.Order
import com.firstgroup.secondhand.core.network.order.model.OrderRequest

interface OrderRepository {

    /**
     * Buyer
     */

    suspend fun createOrder(orderRequest: OrderRequest): CreateOrder

//    suspend fun updateOrder(id: Int): CreateOrderDto

    suspend fun getOrdersAsBuyer(): List<Order>

    suspend fun getOrderByIdAsBuyer(id: Int): Order

    suspend fun getAllOrdersAsSeller(): List<Order>

    suspend fun getAcceptedOrdersAsSeller(): List<Order>

    suspend fun getDeclinedOrdersAsSeller(): List<Order>

    suspend fun getPendingOrdersAsSeller(): List<Order>

}
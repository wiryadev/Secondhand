package com.firstgroup.secondhand.core.data.repositories.order

import com.firstgroup.secondhand.core.model.BasicResponse
import com.firstgroup.secondhand.core.model.CreateOrder
import com.firstgroup.secondhand.core.model.Order
import com.firstgroup.secondhand.core.model.RespondOrder

interface OrderRepository {

    /**
     * Buyer
     */

    suspend fun createOrder(
        productId: Int,
        bidPrice: Int,
    ): CreateOrder

    suspend fun getOrdersAsBuyer(): List<Order>

    suspend fun getOrderByIdAsBuyer(id: Int): Order

    suspend fun updateOrder(
        orderId: Int,
        newBidPrice: Int,
    ): CreateOrder

    suspend fun deleteOrderAsBuyer(id: Int): BasicResponse

    suspend fun getAllOrdersAsSeller(): List<Order>

    suspend fun getAcceptedOrdersAsSeller(): List<Order>

    suspend fun getDeclinedOrdersAsSeller(): List<Order>

    suspend fun getPendingOrdersAsSeller(): List<Order>

    suspend fun getOrderByIdAsSeller(id: Int): Order

    suspend fun acceptOrder(id: Int): RespondOrder

    suspend fun rejectOrder(id: Int): RespondOrder

}
package com.firstgroup.secondhand.core.data.repositories.order

import com.firstgroup.secondhand.core.model.CreateOrder
import com.firstgroup.secondhand.core.model.Order
import com.firstgroup.secondhand.core.network.order.OrderRemoteDataSource
import com.firstgroup.secondhand.core.network.order.model.OrderRequest
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val remoteDataSource: OrderRemoteDataSource
) : OrderRepository {

    override suspend fun createOrder(
        orderRequest: OrderRequest
    ): CreateOrder = remoteDataSource.createOrder(orderRequest).mapToDomain()

//    override suspend fun updateOrder(id: Int): CreateOrderDto {
//
//    }

    override suspend fun getOrdersAsBuyer(): List<Order> {
        return remoteDataSource.getOrdersAsBuyer().map {
            it.mapToDomain()
        }
    }

    override suspend fun getOrderByIdAsBuyer(id: Int): Order {
        return remoteDataSource.getOrderByIdAsBuyer(id).mapToDomain()
    }

}
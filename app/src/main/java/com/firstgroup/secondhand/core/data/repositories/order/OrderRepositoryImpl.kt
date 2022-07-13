package com.firstgroup.secondhand.core.data.repositories.order

import com.firstgroup.secondhand.core.model.BasicResponse
import com.firstgroup.secondhand.core.model.CreateOrder
import com.firstgroup.secondhand.core.model.Order
import com.firstgroup.secondhand.core.model.RespondOrder
import com.firstgroup.secondhand.core.network.order.OrderRemoteDataSource
import com.firstgroup.secondhand.core.network.order.model.OrderRequest
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val remoteDataSource: OrderRemoteDataSource
) : OrderRepository {

    override suspend fun createOrder(
        orderRequest: OrderRequest
    ): CreateOrder = remoteDataSource.createOrder(orderRequest).mapToDomain()

    override suspend fun updateOrder(
        orderId: Int,
        newBidPrice: Int,
    ): CreateOrder = remoteDataSource.updateOrder(
        orderId = orderId,
        newBidPrice = newBidPrice,
    ).mapToDomain()

    override suspend fun deleteOrderAsBuyer(id: Int): BasicResponse =
        remoteDataSource.deleteOrderAsBuyer(id).mapToDomainModel()

    override suspend fun getOrdersAsBuyer(): List<Order> {
        return remoteDataSource.getOrdersAsBuyer().map {
            it.mapToDomain()
        }
    }

    override suspend fun getOrderByIdAsBuyer(id: Int): Order {
        return remoteDataSource.getOrderByIdAsBuyer(id).mapToDomain()
    }

    override suspend fun getAllOrdersAsSeller(): List<Order> {
        return remoteDataSource.getAllOrdersAsSeller().map {
            it.mapToDomain()
        }
    }

    override suspend fun getAcceptedOrdersAsSeller(): List<Order> {
        return remoteDataSource.getAcceptedOrdersAsSeller().map {
            it.mapToDomain()
        }
    }

    override suspend fun getDeclinedOrdersAsSeller(): List<Order> {
        return remoteDataSource.getDeclinedOrdersAsSeller().map {
            it.mapToDomain()
        }
    }

    override suspend fun getPendingOrdersAsSeller(): List<Order> {
        return remoteDataSource.getPendingOrdersAsSeller().map {
            it.mapToDomain()
        }
    }

    override suspend fun getOrderByIdAsSeller(id: Int): Order {
        return remoteDataSource.getOrderByIdAsSeller(id).mapToDomain()
    }

    override suspend fun acceptOrder(id: Int): RespondOrder =
        remoteDataSource.acceptOrder(id).mapToDomainModel()

    override suspend fun rejectOrder(id: Int): RespondOrder =
        remoteDataSource.rejectOrder(id).mapToDomainModel()

}
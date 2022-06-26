package com.firstgroup.secondhand.core.network.order.retrofit

import com.firstgroup.secondhand.core.network.order.model.CreateOrderDto
import com.firstgroup.secondhand.core.network.order.model.GetOrderDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderService {

    /**
     * Buyer
     */

    @POST("buyer/order")
    fun createOrder(
        @Body orderData: RequestBody
    ): CreateOrderDto

    // for seller will be named "respondOrder" or smth like that
    @PUT("buyer/order/{id}")
    fun updateOrder(
        @Path("id") id: Int
    ): CreateOrderDto

    @DELETE("buyer/order/{id}")
    fun deleteOrder(
        @Path("id") id: Int
    )

    @GET("buyer/order")
    fun getOrdersAsBuyer(): List<GetOrderDto>

    @GET("buyer/order/{id}")
    fun getOrderByIdAsBuyer(
        @Path("id") id: Int
    ): List<GetOrderDto>

}
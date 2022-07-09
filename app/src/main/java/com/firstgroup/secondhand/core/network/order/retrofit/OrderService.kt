package com.firstgroup.secondhand.core.network.order.retrofit

import com.firstgroup.secondhand.core.network.order.model.CreateOrderDto
import com.firstgroup.secondhand.core.network.order.model.DeleteOrderDto
import com.firstgroup.secondhand.core.network.order.model.GetOrderDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderService {

    /**
     * Buyer
     */

    @POST("buyer/order")
    suspend fun createOrder(
        @Body orderData: RequestBody
    ): CreateOrderDto

    @GET("buyer/order")
    suspend fun getOrdersAsBuyer(): List<GetOrderDto>

    @GET("buyer/order/{id}")
    suspend fun getOrderByIdAsBuyer(
        @Path("id") id: Int
    ): GetOrderDto

    // for seller will be named "respondOrder" or smth like that
    @FormUrlEncoded
    @PUT("buyer/order/{id}")
    suspend fun updateOrderAsBuyer(
        @Path("id") id: Int,
        @Field("bid_price") bidPrice: Int,
    ): CreateOrderDto

    @DELETE("buyer/order/{id}")
    suspend fun deleteOrderAsBuyer(
        @Path("id") id: Int
    ): DeleteOrderDto


    /**
     * Seller
     */

    @GET("seller/order")
    suspend fun getOrdersAsSeller(
        @Query("status") status: String? = null
    ): List<GetOrderDto>

}
package com.firstgroup.secondhand.core.network.product.retrofit

import com.firstgroup.secondhand.core.network.product.model.CategoryResponse
import com.firstgroup.secondhand.core.network.product.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ProductService {

    /**
     * Buyer
     */
    @GET("buyer/product")
    suspend fun getProductsAsBuyer(
        @Header("access_token") token: String,
    ): List<ProductResponse>

    @GET("buyer/product/{id}")
    suspend fun getProductByIdAsBuyer(
        @Header("access_token") token: String,
        @Path("id") productId: String,
    ): ProductResponse


    /**
     * Seller
     */
    @GET("seller/product")
    suspend fun getProductsAsSeller(
        @Header("access_token") token: String,
    ): List<ProductResponse>

    @GET("seller/product/{id}")
    suspend fun getProductByIdAsSeller(
        @Header("access_token") token: String,
        @Path("id") productId: String,
    ): ProductResponse

    @GET("seller/category")
    suspend fun getCategories(
        @Header("access_token") token: String,
    ): List<CategoryResponse>

}
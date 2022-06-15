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
    fun getProductsAsBuyer(
        @Header("access_token") token: String,
    ): List<ProductResponse>

    @GET("buyer/product/{id}")
    fun getProductByIdAsBuyer(
        @Header("access_token") token: String,
        @Path("id") productId: String,
    ): ProductResponse


    /**
     * Seller
     */
    @GET("seller/product")
    fun getProductsAsSeller(
        @Header("access_token") token: String,
    ): List<ProductResponse>

    @GET("seller/product/{id}")
    fun getProductByIdAsSeller(
        @Header("access_token") token: String,
        @Path("id") productId: String,
    ): ProductResponse

    @GET("seller/category")
    fun getCategories(
        @Header("access_token") token: String,
        @Path("id") productId: String,
    ): List<CategoryResponse>

}
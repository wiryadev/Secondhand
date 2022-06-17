package com.firstgroup.secondhand.core.network.product.retrofit

import com.firstgroup.secondhand.core.network.product.model.CategoryResponse
import com.firstgroup.secondhand.core.network.product.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    /**
     * Buyer
     */
    @GET("buyer/product")
    suspend fun getProductsAsBuyer(): List<ProductResponse>

    @GET("buyer/product/{id}")
    suspend fun getProductByIdAsBuyer(
        @Path("id") productId: String,
    ): ProductResponse


    /**
     * Seller
     */
    @GET("seller/product")
    suspend fun getProductsAsSeller(): List<ProductResponse>

    @GET("seller/product/{id}")
    suspend fun getProductByIdAsSeller(
        @Path("id") productId: String,
    ): ProductResponse

    @GET("seller/category")
    suspend fun getCategories(): List<CategoryResponse>

}
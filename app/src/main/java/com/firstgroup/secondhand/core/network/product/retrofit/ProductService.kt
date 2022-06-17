package com.firstgroup.secondhand.core.network.product.retrofit

import com.firstgroup.secondhand.core.network.product.model.BannerDto
import com.firstgroup.secondhand.core.network.product.model.CategoryDto
import com.firstgroup.secondhand.core.network.product.model.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    /**
     * Buyer
     */
    @GET("buyer/product")
    suspend fun getProductsAsBuyer(): List<ProductDto>

    @GET("buyer/product/{id}")
    suspend fun getProductByIdAsBuyer(
        @Path("id") productId: String,
    ): ProductDto


    /**
     * Seller
     */
    @GET("seller/product")
    suspend fun getProductsAsSeller(): List<ProductDto>

    @GET("seller/product/{id}")
    suspend fun getProductByIdAsSeller(
        @Path("id") productId: String,
    ): ProductDto

    @GET("seller/category")
    suspend fun getCategories(): List<CategoryDto>

    @GET("seller/banner")
    suspend fun getBanners(): List<BannerDto>

}
package com.firstgroup.secondhand.core.network.product

import com.firstgroup.secondhand.core.network.product.model.CategoryResponse
import com.firstgroup.secondhand.core.network.product.model.ProductResponse

interface ProductRemoteDataSource {

    /**
     * Buyer
     */
    suspend fun getProductsAsBuyer(
        token: String,
    ): List<ProductResponse>

    suspend fun getProductByIdAsBuyer(
        token: String,
        productId: String,
    ): ProductResponse


    /**
     * Seller
     */
    suspend fun getProductsAsSeller(
        token: String,
    ): List<ProductResponse>

    suspend fun getProductByIdAsSeller(
        token: String,
        productId: String,
    ): ProductResponse

    suspend fun getCategories(
        token: String,
    ): List<CategoryResponse>
}
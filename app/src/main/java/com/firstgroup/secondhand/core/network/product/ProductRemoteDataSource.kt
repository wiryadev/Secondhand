package com.firstgroup.secondhand.core.network.product

import com.firstgroup.secondhand.core.network.product.model.CategoryResponse
import com.firstgroup.secondhand.core.network.product.model.ProductResponse

interface ProductRemoteDataSource {

    /**
     * Buyer
     */
    suspend fun getProductsAsBuyer(): List<ProductResponse>

    suspend fun getProductByIdAsBuyer(
        productId: String,
    ): ProductResponse


    /**
     * Seller
     */
    suspend fun getProductsAsSeller(): List<ProductResponse>

    suspend fun getProductByIdAsSeller(
        productId: String,
    ): ProductResponse

    suspend fun getCategories(): List<CategoryResponse>
}
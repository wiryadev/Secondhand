package com.firstgroup.secondhand.core.network.product

import com.firstgroup.secondhand.core.network.product.model.BannerDto
import com.firstgroup.secondhand.core.network.product.model.CategoryDto
import com.firstgroup.secondhand.core.network.product.model.ProductDto

interface ProductRemoteDataSource {

    /**
     * Buyer
     */
    suspend fun getProductsAsBuyer(): List<ProductDto>

    suspend fun getProductByIdAsBuyer(
        productId: String,
    ): ProductDto


    /**
     * Seller
     */
    suspend fun getProductsAsSeller(): List<ProductDto>

    suspend fun getProductByIdAsSeller(
        productId: String,
    ): ProductDto

    suspend fun getCategories(): List<CategoryDto>

    suspend fun getBanners(): List<BannerDto>
}
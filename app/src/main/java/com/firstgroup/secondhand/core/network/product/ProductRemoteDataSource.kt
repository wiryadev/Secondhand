package com.firstgroup.secondhand.core.network.product

import com.firstgroup.secondhand.core.network.product.model.*

interface ProductRemoteDataSource {

    /**
     * Buyer
     */
    suspend fun getProductsAsBuyer(): List<ProductDto>

    suspend fun getProductsByCategory(categoryId: Int): List<ProductDto>

    suspend fun searchProducts(query: String): List<ProductDto>

    suspend fun getProductByIdAsBuyer(
        productId: Int,
    ): ProductDetailDto


    /**
     * Seller
     */
    suspend fun getProductsAsSeller(): List<ProductDto>

    suspend fun getProductByIdAsSeller(
        productId: Int,
    ): ProductDto

    suspend fun addNewProduct(
        productRequest: ProductRequest
    ): AddProductDto

    suspend fun getCategories(): List<CategoryDto>

    suspend fun getBanners(): List<BannerDto>
}
package com.firstgroup.secondhand.core.data.repositories.product

import com.firstgroup.secondhand.core.model.Banner
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.core.network.product.model.ProductRequest
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    /**
     * Buyer
     */

    suspend fun loadBuyerProducts()

    fun getProductsAsBuyer(): Flow<List<Product>>

    suspend fun deleteCachedProducts()

    suspend fun deleteWishlist()

    /**
     * Seller
     */
    suspend fun getProductsAsSeller(): List<Product>

    suspend fun addNewProduct(
        productRequest: ProductRequest
    ): Product

    suspend fun loadCategories()

    fun getCategories(): Flow<List<Category>>

    suspend fun getBanner(): List<Banner>

    suspend fun deleteCachedCategories()

}
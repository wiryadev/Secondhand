package com.firstgroup.secondhand.core.data.repositories.product

import androidx.paging.PagingData
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

    fun getProductsAsBuyer(): Flow<PagingData<Product>>

    suspend fun getProductsByCategory(categoryId: Int): List<Product>

    suspend fun searchProducts(query: String): List<Product>

    suspend fun getProductByIdAsBuyer(id: Int): Product

    suspend fun deleteCachedProducts()

    suspend fun deleteWishlist()

    /**
     * Seller
     */
    suspend fun getProductsAsSeller(): List<Product>

    suspend fun getProductByIdAsSeller(id: Int): Product

    suspend fun addNewProduct(
        productRequest: ProductRequest
    ): Product

    suspend fun loadCategories()

    suspend fun getCategories(): List<Category>

    suspend fun getBanner(): List<Banner>

    suspend fun deleteCachedCategories()

}
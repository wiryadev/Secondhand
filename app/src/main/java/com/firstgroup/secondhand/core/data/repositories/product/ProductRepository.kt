package com.firstgroup.secondhand.core.data.repositories.product

import androidx.paging.PagingData
import com.firstgroup.secondhand.core.database.product.entity.ProductEntity
import com.firstgroup.secondhand.core.model.Banner
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.core.network.product.model.ProductDto
import com.firstgroup.secondhand.core.network.product.model.ProductRequest
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    /**
     * Buyer
     */

    fun getProductsAsBuyer(): Flow<PagingData<ProductEntity>>

    fun getProductsByCategory(categoryId: Int): Flow<PagingData<ProductDto>>

    fun searchProducts(query: String): Flow<PagingData<ProductDto>>

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
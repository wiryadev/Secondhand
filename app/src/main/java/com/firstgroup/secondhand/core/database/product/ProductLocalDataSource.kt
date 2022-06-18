package com.firstgroup.secondhand.core.database.product

import com.firstgroup.secondhand.core.database.product.entity.CategoryEntity
import com.firstgroup.secondhand.core.database.product.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductLocalDataSource {

    fun getCachedProducts(): Flow<List<ProductEntity>>

    suspend fun cacheAllProducts(products: List<ProductEntity>)

    suspend fun deleteCachedProducts()

    fun getCachedCategories(): Flow<List<CategoryEntity>>

    suspend fun cacheAllCategories(categories: List<CategoryEntity>)

    suspend fun deleteCachedCategories()

    suspend fun deleteWishlist()

}
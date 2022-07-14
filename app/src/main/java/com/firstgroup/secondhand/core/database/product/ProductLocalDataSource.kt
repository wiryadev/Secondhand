package com.firstgroup.secondhand.core.database.product

import androidx.paging.PagingSource
import com.firstgroup.secondhand.core.database.product.entity.CategoryEntity
import com.firstgroup.secondhand.core.database.product.entity.ProductEntity
import com.firstgroup.secondhand.core.database.product.entity.RemoteKeys

interface ProductLocalDataSource {

    suspend fun <R> cacheProductTransaction(block: suspend () -> R): R

    fun getCachedProducts(): PagingSource<Int, ProductEntity>

    suspend fun cacheAllProducts(products: List<ProductEntity>)

    suspend fun deleteCachedProducts()

    suspend fun insertRemoteKeys(remoteKeys: List<RemoteKeys>)

    suspend fun getRemoteKeysId(productId: Int): RemoteKeys?


    suspend fun clearRemoteKeys()

    suspend fun getCachedCategories(): List<CategoryEntity>

    suspend fun cacheAllCategories(categories: List<CategoryEntity>)

    suspend fun deleteCachedCategories()

    suspend fun deleteWishlist()

}
package com.firstgroup.secondhand.core.database.product

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.firstgroup.secondhand.core.database.SecondhandDatabase
import com.firstgroup.secondhand.core.database.product.dao.CategoryDao
import com.firstgroup.secondhand.core.database.product.dao.ProductCacheDao
import com.firstgroup.secondhand.core.database.product.dao.RemoteKeysDao
import com.firstgroup.secondhand.core.database.product.dao.WishlistDao
import com.firstgroup.secondhand.core.database.product.entity.CategoryEntity
import com.firstgroup.secondhand.core.database.product.entity.ProductEntity
import com.firstgroup.secondhand.core.database.product.entity.RemoteKeys
import javax.inject.Inject

class ProductLocalDataSourceImpl @Inject constructor(
    private val database: SecondhandDatabase,
    private val productCacheDao: ProductCacheDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val categoryDao: CategoryDao,
    private val wishlistDao: WishlistDao,
) : ProductLocalDataSource {

    override suspend fun <R> cacheProductTransaction(block: suspend () -> R): R {
        return database.withTransaction(block)
    }

    override fun getCachedProducts(): PagingSource<Int, ProductEntity> {
        return productCacheDao.getCachedProducts()
    }

    override suspend fun cacheAllProducts(products: List<ProductEntity>) {
        productCacheDao.insertOrReplace(products)
    }

    override suspend fun clearCachedProducts() {
        productCacheDao.deleteAll()
    }

    override suspend fun insertRemoteKeys(remoteKeys: List<RemoteKeys>) {
        remoteKeysDao.insertAll(remoteKeys)
    }

    override suspend fun getRemoteKeysId(productId: Int): RemoteKeys? {
        return remoteKeysDao.getRemoteKeysId(productId)
    }

    override suspend fun clearRemoteKeys() {
        remoteKeysDao.clearRemoteKeys()
    }

    override suspend fun getCachedCategories(): List<CategoryEntity> {
        return categoryDao.getCachedCategories()
    }

    override suspend fun cacheAllCategories(categories: List<CategoryEntity>) {
        categoryDao.insertOrReplace(categories)
    }

    override suspend fun deleteCachedCategories() {
        categoryDao.deleteAll()
    }

    override suspend fun deleteWishlist() {
        wishlistDao.deleteAll()
    }

}
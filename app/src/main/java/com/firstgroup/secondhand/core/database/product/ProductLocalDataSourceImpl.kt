package com.firstgroup.secondhand.core.database.product

import com.firstgroup.secondhand.core.database.product.dao.CategoryDao
import com.firstgroup.secondhand.core.database.product.dao.ProductCacheDao
import com.firstgroup.secondhand.core.database.product.dao.WishlistDao
import com.firstgroup.secondhand.core.database.product.entity.CategoryEntity
import com.firstgroup.secondhand.core.database.product.entity.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductLocalDataSourceImpl @Inject constructor(
    private val productCacheDao: ProductCacheDao,
    private val categoryDao: CategoryDao,
    private val wishlistDao: WishlistDao,
) : ProductLocalDataSource {

    override fun getCachedProducts(): Flow<List<ProductEntity>> {
        return productCacheDao.getCachedProducts()
    }

    override suspend fun cacheAllProducts(products: List<ProductEntity>) {
        productCacheDao.insertOrReplace(products)
    }

    override suspend fun deleteCachedProducts() {
        productCacheDao.deleteAll()
    }

    override fun getCachedCategories(): Flow<List<CategoryEntity>> {
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
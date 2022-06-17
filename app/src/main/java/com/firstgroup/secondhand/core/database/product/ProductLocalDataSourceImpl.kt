package com.firstgroup.secondhand.core.database.product

import com.firstgroup.secondhand.core.database.product.dao.ProductCacheDao
import com.firstgroup.secondhand.core.database.product.entity.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductLocalDataSourceImpl @Inject constructor(
    private val dao: ProductCacheDao,
) : ProductLocalDataSource {

    override fun getCachedProducts(): Flow<List<ProductEntity>> {
       return dao.getAll()
    }

    override suspend fun cacheAllProducts(products: List<ProductEntity>) {
        return dao.addAll(products)
    }

}
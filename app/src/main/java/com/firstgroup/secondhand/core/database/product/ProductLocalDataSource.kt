package com.firstgroup.secondhand.core.database.product

import com.firstgroup.secondhand.core.database.product.entity.ProductEntity

interface ProductLocalDataSource {

    suspend fun getCachedProducts(): List<ProductEntity>

    suspend fun cacheAllProducts(products: List<ProductEntity>)

}
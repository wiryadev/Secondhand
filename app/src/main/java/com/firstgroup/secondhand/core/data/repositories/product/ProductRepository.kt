package com.firstgroup.secondhand.core.data.repositories.product

import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.core.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    /**
     * Buyer
     */

    suspend fun loadBuyerProducts()

    fun getProductsAsBuyer(): Flow<List<Product>>

    /**
     * Seller
     */
    suspend fun loadCategories()

    fun getCategories(): Flow<List<Category>>

}
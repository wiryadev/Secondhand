package com.firstgroup.secondhand.core.data.repositories.product

import com.firstgroup.secondhand.core.model.Product

interface ProductRepository {

    suspend fun getProductsAsBuyer(
        token: String
    ): List<Product>

}
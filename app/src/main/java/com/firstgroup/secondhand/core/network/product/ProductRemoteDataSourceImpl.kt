package com.firstgroup.secondhand.core.network.product

import com.firstgroup.secondhand.core.network.product.model.CategoryResponse
import com.firstgroup.secondhand.core.network.product.model.ProductResponse
import com.firstgroup.secondhand.core.network.product.retrofit.ProductService
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val service: ProductService,
) : ProductRemoteDataSource {

    override suspend fun getProductsAsBuyer(
        token: String
    ): List<ProductResponse> = service.getProductsAsBuyer(token)

    override suspend fun getProductByIdAsBuyer(
        token: String,
        productId: String,
    ): ProductResponse = service.getProductByIdAsBuyer(token, productId)

    override suspend fun getProductsAsSeller(
        token: String
    ): List<ProductResponse> = service.getProductsAsSeller(token)

    override suspend fun getProductByIdAsSeller(
        token: String,
        productId: String,
    ): ProductResponse = service.getProductByIdAsSeller(token, productId)

    override suspend fun getCategories(
        token: String
    ): List<CategoryResponse> = service.getCategories(token)

}
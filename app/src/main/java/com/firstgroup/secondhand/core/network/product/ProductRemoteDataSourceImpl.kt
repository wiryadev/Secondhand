package com.firstgroup.secondhand.core.network.product

import com.firstgroup.secondhand.core.network.product.model.CategoryResponse
import com.firstgroup.secondhand.core.network.product.model.ProductResponse
import com.firstgroup.secondhand.core.network.product.retrofit.ProductService
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val service: ProductService,
) : ProductRemoteDataSource {

    override suspend fun getProductsAsBuyer(): List<ProductResponse> =
        service.getProductsAsBuyer()

    override suspend fun getProductByIdAsBuyer(
        productId: String,
    ): ProductResponse = service.getProductByIdAsBuyer(productId)

    override suspend fun getProductsAsSeller(): List<ProductResponse> = service.getProductsAsSeller()

    override suspend fun getProductByIdAsSeller(
        productId: String,
    ): ProductResponse = service.getProductByIdAsSeller(productId)

    override suspend fun getCategories(
    ): List<CategoryResponse> = service.getCategories()

}
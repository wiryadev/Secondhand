package com.firstgroup.secondhand.core.network.product

import com.firstgroup.secondhand.core.network.product.model.BannerDto
import com.firstgroup.secondhand.core.network.product.model.CategoryDto
import com.firstgroup.secondhand.core.network.product.model.ProductDto
import com.firstgroup.secondhand.core.network.product.retrofit.ProductService
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val service: ProductService,
) : ProductRemoteDataSource {

    override suspend fun getProductsAsBuyer(): List<ProductDto> =
        service.getProductsAsBuyer()

    override suspend fun getProductByIdAsBuyer(
        productId: String,
    ): ProductDto = service.getProductByIdAsBuyer(productId)

    override suspend fun getProductsAsSeller(): List<ProductDto> = service.getProductsAsSeller()

    override suspend fun getProductByIdAsSeller(
        productId: String,
    ): ProductDto = service.getProductByIdAsSeller(productId)

    override suspend fun getCategories(): List<CategoryDto> = service.getCategories()

    override suspend fun getBanners(): List<BannerDto> = service.getBanners()

}
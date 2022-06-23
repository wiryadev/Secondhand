package com.firstgroup.secondhand.core.network.product

import com.firstgroup.secondhand.core.network.product.model.*
import com.firstgroup.secondhand.core.network.product.retrofit.ProductService
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val service: ProductService,
) : ProductRemoteDataSource {

    override suspend fun getProductsAsBuyer(): List<ProductDto> =
        service.getProductsAsBuyer()

    override suspend fun getProductByIdAsBuyer(
        productId: Int,
    ): ProductDto = service.getProductByIdAsBuyer(productId)

    override suspend fun getProductsAsSeller(): List<ProductDto> = service.getProductsAsSeller()

    override suspend fun getProductByIdAsSeller(
        productId: Int,
    ): ProductDto = service.getProductByIdAsSeller(productId)

    override suspend fun addNewProduct(productRequest: ProductRequest): AddProductDto {
        val formData = productRequest.toFormData()
        return service.addNewProduct(
            partMap = formData.requestBody,
            image = formData.multipart,
            categoryIds = formData.categoryIds
        )
    }

    override suspend fun getCategories(): List<CategoryDto> = service.getCategories()

    override suspend fun getBanners(): List<BannerDto> = service.getBanners()

}
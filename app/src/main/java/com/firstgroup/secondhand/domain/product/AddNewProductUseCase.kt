package com.firstgroup.secondhand.domain.product

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.product.ProductRepository
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.core.network.product.model.ProductRequest
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import java.io.File
import javax.inject.Inject

class AddNewProductUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<AddNewProductUseCase.Param, Product>(ioDispatcher) {

    override suspend fun execute(param: Param): Product {
        val request = ProductRequest(
            name = param.name,
            description = param.description,
            basePrice = param.basePrice,
            categoryIds = param.categoryIds,
            location = param.location,
            image = param.image,
        )
        return productRepository.addNewProduct(request)
    }

    data class Param(
        val name: String,
        val description: String,
        val basePrice: Int,
        val categoryIds: List<Int>,
        val location: String,
        val image: File,
    )

}
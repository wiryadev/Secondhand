package com.firstgroup.secondhand.domain.product

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.product.ProductRepository
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
): UseCase<Category, List<Product>>(ioDispatcher) {

    override suspend fun execute(param: Category): List<Product> {
        return productRepository.getProductsByCategory(param.id)
    }

}
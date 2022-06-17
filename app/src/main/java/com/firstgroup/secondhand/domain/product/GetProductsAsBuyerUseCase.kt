package com.firstgroup.secondhand.domain.product

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.product.ProductRepository
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.domain.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsAsBuyerUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : FlowUseCase<Any, List<Product>>(ioDispatcher) {

    override suspend fun execute(param: Any): Flow<List<Product>> {
        productRepository.loadBuyerProducts()
        return productRepository.getProductsAsBuyer()
    }

}
package com.firstgroup.secondhand.domain.product

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers
import com.firstgroup.secondhand.core.data.repositories.product.ProductRepository
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.domain.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    @AppDispatcher(SecondhandDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : FlowUseCase<Any, List<Category>>(ioDispatcher) {

    override fun execute(param: Any): Flow<List<Category>> {
        return productRepository.getCategories()
    }

}
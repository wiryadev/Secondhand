package com.firstgroup.secondhand.domain.product

import androidx.paging.PagingData
import com.firstgroup.secondhand.core.data.repositories.product.ProductRepository
import com.firstgroup.secondhand.core.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsAsBuyerUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {

    operator fun invoke(): Flow<PagingData<Product>> {
        return productRepository.getProductsAsBuyer()
    }

}
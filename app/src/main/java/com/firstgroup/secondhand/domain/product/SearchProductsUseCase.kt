package com.firstgroup.secondhand.domain.product

import androidx.paging.PagingData
import androidx.paging.map
import com.firstgroup.secondhand.core.data.repositories.product.ProductRepository
import com.firstgroup.secondhand.core.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
)  {

    operator fun invoke(param: String): Flow<PagingData<Product>> {
        return productRepository.searchProducts(param).map { pagingData ->
            pagingData.map { it.mapToDomainModel() }
        }
    }

}
package com.firstgroup.secondhand.domain.product

import android.util.Log
import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.product.ProductRepository
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RefreshProductsUseCase @Inject constructor(
    private val repository: ProductRepository,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<Any, Unit>(ioDispatcher) {

    override suspend fun execute(param: Any) {
        Log.d("ProductCache", "useCase: executed")
        repository.loadBuyerProducts()
    }

}
package com.firstgroup.secondhand.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.domain.product.GetCategoriesUseCase
import com.firstgroup.secondhand.domain.product.GetProductsAsBuyerUseCase
import com.firstgroup.secondhand.domain.product.RefreshCategoriesUseCase
import com.firstgroup.secondhand.domain.product.RefreshProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val refreshProductsUseCase: RefreshProductsUseCase,
    private val getProductsAsBuyerUseCase: GetProductsAsBuyerUseCase,
    private val refreshCategoriesUseCase: RefreshCategoriesUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    init {
        viewModelScope.launch {
            refreshProductsUseCase(Unit)
            refreshCategoriesUseCase(Unit)
        }
    }

    val uiState: StateFlow<HomeUiState> = combine(
        getProductsAsBuyerUseCase(Unit),
        getCategoriesUseCase(Unit),
    ) { buyerProductsResult, categoriesResult ->
        val products: BuyerProductsUiState = when (buyerProductsResult) {
            is Result.Error -> BuyerProductsUiState.Error
            is Result.Success -> BuyerProductsUiState.Success(buyerProductsResult.data)
        }

        val categories: CategoriesUiState = when (categoriesResult) {
            is Result.Error -> CategoriesUiState.Error
            is Result.Success -> CategoriesUiState.Success(categoriesResult.data)
        }

        HomeUiState(products, categories)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState(BuyerProductsUiState.Loading, CategoriesUiState.Loading)
    )

}

sealed interface BuyerProductsUiState {
    data class Success(val products: List<Product>) : BuyerProductsUiState
    object Error : BuyerProductsUiState
    object Loading : BuyerProductsUiState
}

sealed interface CategoriesUiState {
    data class Success(val categories: List<Category>) : CategoriesUiState
    object Error : CategoriesUiState
    object Loading : CategoriesUiState
}

data class HomeUiState(
    val productState: BuyerProductsUiState,
    val categoryState: CategoriesUiState,
)
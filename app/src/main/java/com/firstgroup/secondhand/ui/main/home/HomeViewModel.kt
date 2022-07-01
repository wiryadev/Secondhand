package com.firstgroup.secondhand.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.domain.product.GetCategoriesUseCase
import com.firstgroup.secondhand.domain.product.GetProductsAsBuyerUseCase
import com.firstgroup.secondhand.domain.product.RefreshCategoriesUseCase
import com.firstgroup.secondhand.domain.product.RefreshProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val refreshProductsUseCase: RefreshProductsUseCase,
    private val getProductsAsBuyerUseCase: GetProductsAsBuyerUseCase,
    private val refreshCategoriesUseCase: RefreshCategoriesUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    val products = getProductsAsBuyerUseCase().cachedIn(viewModelScope)

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> get() = _uiState

    private val allCategories = listOf(
        Category(id = -1, name = "Semua"),
    )

    init {
        viewModelScope.launch {
            refreshProductLocalCache()
            getCategories()
        }
    }

    private suspend fun refreshProductLocalCache() {
        refreshProductsUseCase(Unit)
        _uiState.update {
            it.copy(productState = BuyerProductsUiState.Loaded)
        }
    }

    private suspend fun getCategories() {
        refreshCategoriesUseCase(Unit)
        when (val result = getCategoriesUseCase(Unit)) {
            is Result.Error -> {
                _uiState.update {
                    it.copy(categoryState = CategoriesUiState.Error)
                }
            }
            is Result.Success -> {
                _uiState.update {
                    it.copy(
                        categoryState = CategoriesUiState.Success(
                            categories = allCategories + result.data
                        )
                    )
                }
            }
        }
    }

}

sealed interface BuyerProductsUiState {
    object Loaded : BuyerProductsUiState
    object Loading : BuyerProductsUiState
}

sealed interface CategoriesUiState {
    data class Success(val categories: List<Category>) : CategoriesUiState
    object Error : CategoriesUiState
    object Loading : CategoriesUiState
}

data class HomeUiState(
    val productState: BuyerProductsUiState = BuyerProductsUiState.Loading,
    val categoryState: CategoriesUiState = CategoriesUiState.Loading,
)
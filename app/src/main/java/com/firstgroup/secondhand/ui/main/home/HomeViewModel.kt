package com.firstgroup.secondhand.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.domain.product.*
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
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
) : ViewModel() {

    private val allCategories = listOf(
        Category(id = DEFAULT_SELECTED_CATEGORY_ID, name = "Semua"),
    )
    val products = getProductsAsBuyerUseCase().cachedIn(viewModelScope)

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(
        HomeUiState(selectedCategory = allCategories[0])
    )
    val uiState: StateFlow<HomeUiState> get() = _uiState

    init {
        viewModelScope.launch {
            refreshProductLocalCache()
            getCategories()
        }
    }

    private suspend fun refreshProductLocalCache() {
        refreshProductsUseCase(Unit)
        _uiState.update {
            it.copy(allProductState = AllProductsUiState.Loaded)
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

    fun setCategory(category: Category) {
        _uiState.update {
            it.copy(selectedCategory = category)
        }

        // only update productByCategory if selected category is not default
        if (category.id != DEFAULT_SELECTED_CATEGORY_ID) {
            _uiState.update {
                it.copy(productsByCategoryState = ProductByCategoryUiState.Loading)
            }
            viewModelScope.launch {
                when (val result = getProductsByCategoryUseCase(category)) {
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                productsByCategoryState = ProductByCategoryUiState.Error(result.exception?.message)
                            )
                        }
                    }
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                productsByCategoryState = ProductByCategoryUiState.Success(result.data)
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val DEFAULT_SELECTED_CATEGORY_ID = 1
    }

}

sealed interface AllProductsUiState {
    object Loaded : AllProductsUiState
    object Loading : AllProductsUiState
}

sealed interface ProductByCategoryUiState {
    data class Success(val products: List<Product>) : ProductByCategoryUiState
    data class Error(val message: String?) : ProductByCategoryUiState
    object Loading : ProductByCategoryUiState
}

sealed interface CategoriesUiState {
    data class Success(val categories: List<Category>) : CategoriesUiState
    object Error : CategoriesUiState
    object Loading : CategoriesUiState
}

data class HomeUiState(
    val allProductState: AllProductsUiState = AllProductsUiState.Loading,
    val productsByCategoryState: ProductByCategoryUiState = ProductByCategoryUiState.Loading,
    val categoryState: CategoriesUiState = CategoriesUiState.Loading,
    val selectedCategory: Category,
)
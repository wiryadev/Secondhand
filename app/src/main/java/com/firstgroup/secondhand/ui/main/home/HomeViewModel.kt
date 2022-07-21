package com.firstgroup.secondhand.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.domain.product.GetCategoriesUseCase
import com.firstgroup.secondhand.domain.product.GetProductsAsBuyerUseCase
import com.firstgroup.secondhand.domain.product.GetProductsByCategoryUseCase
import com.firstgroup.secondhand.domain.product.RefreshCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsAsBuyerUseCase: GetProductsAsBuyerUseCase,
    private val refreshCategoriesUseCase: RefreshCategoriesUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
) : ViewModel() {

    private val allCategories = listOf(
        Category(id = DEFAULT_SELECTED_CATEGORY_ID, name = "Semua"),
    )

    val products: Flow<PagingData<Product>>

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(
        HomeUiState(selectedCategory = allCategories[0])
    )
    val uiState: StateFlow<HomeUiState> get() = _uiState

    init {
        products = _uiState
            .flatMapLatest {
                val data = if (it.selectedCategory.id == DEFAULT_SELECTED_CATEGORY_ID) {
                    getProductsAsBuyerUseCase()
                } else {
                    getProductsByCategoryUseCase(it.selectedCategory)
                }
                showDataWhenLoaded()
                return@flatMapLatest data
            }
            .cachedIn(viewModelScope)

        viewModelScope.launch {
            getCategories()
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
        // do not update if category is the same as before
        if (category.id == _uiState.value.selectedCategory.id) return

        _uiState.update {
            it.copy(
                selectedCategory = category,
                productsByCategoryState = ProductByCategoryUiState.Loading,
            )
        }
    }

    private fun showDataWhenLoaded() {
        _uiState.update { uiState ->
            uiState.copy(
                productsByCategoryState = ProductByCategoryUiState.Loaded,
            )
        }
    }

    companion object {
        const val DEFAULT_SELECTED_CATEGORY_ID = 0
    }

}

sealed interface ProductByCategoryUiState {
    object Loaded : ProductByCategoryUiState
    object Loading : ProductByCategoryUiState
}

sealed interface CategoriesUiState {
    data class Success(val categories: List<Category>) : CategoriesUiState
    object Error : CategoriesUiState
    object Loading : CategoriesUiState
}

data class HomeUiState(
    val productsByCategoryState: ProductByCategoryUiState = ProductByCategoryUiState.Loading,
    val categoryState: CategoriesUiState = CategoriesUiState.Loading,
    val selectedCategory: Category,
)
package com.firstgroup.secondhand.ui.main.home.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.domain.product.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> get() = _uiState


    fun searchProducts(query: String) {
        viewModelScope.launch {
            when (val result = searchProductsUseCase(query)) {
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            searchProductState = ProductsBySearchState.Error(result.exception?.message)
                        )
                    }
                    Log.d("search2", "searchProductsError: ${result.exception?.message}")
                }
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            searchProductState = ProductsBySearchState.Success(result.data)
                        )
                    }
                    Log.d("search2", "searchProducts: ${result.data}")
                }
            }
        }
    }
}

data class SearchUiState(
    val searchProductState: ProductsBySearchState = ProductsBySearchState.Loading
)

sealed interface ProductsBySearchState {
    data class Success(val products: List<Product>) : ProductsBySearchState
    data class Error(val message: String?) : ProductsBySearchState
    object Loading : ProductsBySearchState
}
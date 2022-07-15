package com.firstgroup.secondhand.ui.main.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.domain.product.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> get() = _uiState

    val products: Flow<PagingData<Product>> = _uiState
        .flatMapLatest {
            searchProductsUseCase(it.query)
        }
        .cachedIn(viewModelScope)

    fun searchProducts(query: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(query = query)
            }
        }
    }
}

data class SearchUiState(
    val query: String = DEFAULT_QUERY,
)

private const val DEFAULT_QUERY = ""
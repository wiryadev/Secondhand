package com.firstgroup.secondhand.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.domain.auth.GetUserUseCase
import com.firstgroup.secondhand.domain.product.GetProductByIdAsBuyerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductByIdAsBuyerUseCase: GetProductByIdAsBuyerUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> get() = _uiState

    fun getProductDetailById(id: Int) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when(val result = getProductByIdAsBuyerUseCase(id)) {
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            errorMessage = result.exception?.message,
                            isLoading = false,
                        )
                    }
                }
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            product = result.data,
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }

    fun checkUser() {
        viewModelScope.launch {
            when(val result = getUserUseCase(Unit)) {
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            errorMessage = result.exception?.message,
                        )
                    }
                }
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            user = result.data,
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }

}

data class DetailUiState(
    val product: Product? = null,
    val user: User? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
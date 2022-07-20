package com.firstgroup.secondhand.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.domain.auth.GetSessionUseCase
import com.firstgroup.secondhand.domain.product.GetProductByIdAsBuyerUseCase
import com.firstgroup.secondhand.domain.wishlist.AddToWishlistUseCase
import com.firstgroup.secondhand.domain.wishlist.GetWishlistUseCase
import com.firstgroup.secondhand.ui.auth.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSessionUseCase: GetSessionUseCase,
    private val getProductByIdAsBuyerUseCase: GetProductByIdAsBuyerUseCase,
    private val getWishlistUseCase: GetWishlistUseCase,
    private val addToWishListUseCase: AddToWishlistUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> get() = _uiState

    fun getSession() {
        viewModelScope.launch {
            getSessionUseCase(Unit).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                loginState = LoginState.Loaded(isLoggedIn = false)
                            )
                        }
                    }
                    is Result.Success -> {
                        val token = result.data.token
                        _uiState.update {
                            it.copy(
                                loginState = LoginState.Loaded(
                                    isLoggedIn = token.isNotEmpty()
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun getProductDetailById(id: Int) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val result = getProductByIdAsBuyerUseCase(id)) {
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

    fun getWishlist(id: Int) {
        viewModelScope.launch {
            when (val result = getWishlistUseCase(Unit)) {
                is Result.Success -> {
                    val isWishListed = result.data.any {
                        it.product.id == id
                    }
                    _uiState.update {
                        it.copy(isWishListed = isWishListed)
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(errorMessage = result.exception?.message.toString())
                    }
                }
            }
        }
    }

    fun addToWishlist(id: Int) {
        viewModelScope.launch {
            when (val result = addToWishListUseCase(id)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(isSuccess = true)
                    }
                    getWishlist(id)
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(errorMessage = result.exception?.message.toString())
                    }
                }
            }
        }
    }

}

data class DetailUiState(
    val loginState: LoginState = LoginState.Idle,
    val product: Product? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isWishListed: Boolean = false,
    val isSuccess: Boolean = false
)
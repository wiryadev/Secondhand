package com.firstgroup.secondhand.ui.main.sell_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Order
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.domain.auth.GetSessionUseCase
import com.firstgroup.secondhand.domain.auth.GetUserUseCase
import com.firstgroup.secondhand.domain.order.GetOrdersAsSellerUseCase
import com.firstgroup.secondhand.domain.order.OrderFilter
import com.firstgroup.secondhand.domain.product.GetProductsAsSellerUseCase
import com.firstgroup.secondhand.ui.auth.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellListViewModel @Inject constructor(
    private val getProductsAsSellerUseCase: GetProductsAsSellerUseCase,
    private val getSessionUseCase: GetSessionUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getOrdersAsSellerUseCase: GetOrdersAsSellerUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<SellListUiState> = MutableStateFlow(
        SellListUiState()
    )
    val uiState: StateFlow<SellListUiState> get() = _uiState

    fun getProductAsSeller(){
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val result = getProductsAsSellerUseCase(Unit)){
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            product = result.data,
                            isLoading = false
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            errorMessage = result.exception?.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun getOrderAsSeller(){
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val result = getOrdersAsSellerUseCase(OrderFilter.ALlOrders)){
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            order = result.data,
                            isLoading = false
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            errorMessage = result.exception?.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun getSession(){
        viewModelScope.launch {
            getSessionUseCase(Unit).collect { result ->
                when (result) {
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
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                loginState = LoginState.Loaded(isLoggedIn = false)
                            )
                        }
                    }
                }
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            when (val result = getUserUseCase(Unit)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(recentUser = result.data)
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
}

data class SellListUiState(
    val product : List<Product>? = null,
    val order: List<Order>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val loginState: LoginState = LoginState.Idle,
    val recentUser: User? = null
)
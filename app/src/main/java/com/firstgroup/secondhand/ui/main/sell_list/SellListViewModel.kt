package com.firstgroup.secondhand.ui.main.sell_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Order
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.domain.auth.GetSessionUseCase
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
    private val getOrdersAsSellerUseCase: GetOrdersAsSellerUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<SellListUiState> = MutableStateFlow(SellListUiState())
    val uiState: StateFlow<SellListUiState> get() = _uiState

    fun setFilter(filter: OrderFilter) {
        if (filter == _uiState.value.selectedFilter) return
        _uiState.update {
            it.copy(selectedFilter = filter)
        }
        getOrderAsSeller(filter)
    }

    fun getProductAsSeller() {
        _uiState.update {
            it.copy(productState = SellerProductState.Loading)
        }
        viewModelScope.launch {
            when (val result = getProductsAsSellerUseCase(Unit)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            productState = SellerProductState.Success(result.data)
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            productState = SellerProductState.Error(result.exception?.message.toString()),
                        )
                    }
                }
            }
        }
    }

    fun getOrderAsSeller(filter: OrderFilter) {
        _uiState.update {
            it.copy(orderState = OrderState.Loading)
        }
        viewModelScope.launch {
            when (val result = getOrdersAsSellerUseCase(filter)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            orderState = OrderState.Success(result.data)
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            orderState = OrderState.Error(result.exception?.message.toString())
                        )
                    }
                }
            }
        }
    }

    fun getSession() {
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

    fun refreshProduct(){
        _uiState.update {
            it.copy(
                productState = SellerProductState.Loading
            )
        }
        getProductAsSeller()
    }

    fun refreshOrderAsSeller(){
        _uiState.update {
            it.copy(
                orderState = OrderState.Loading
            )
        }
        getOrderAsSeller(filter = uiState.value.selectedFilter)
    }
}

data class SellListUiState(
    val selectedFilter: OrderFilter = OrderFilter.AllOrders,
    val productState: SellerProductState = SellerProductState.Loading,
    val orderState: OrderState = OrderState.Loading,
    val loginState: LoginState = LoginState.Idle,
)

sealed interface OrderState {
    object Loading : OrderState
    data class Success(val data: List<Order>) : OrderState
    data class Error(val message: String) : OrderState
}

sealed interface SellerProductState {
    object Loading : SellerProductState
    data class Success(val data: List<Product>) : SellerProductState
    data class Error(val message: String) : SellerProductState
}
package com.firstgroup.secondhand.ui.detail.create_order

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.core.network.order.model.OrderRequest
import com.firstgroup.secondhand.domain.order.CreateOrderUseCase
import com.firstgroup.secondhand.domain.product.GetProductByIdAsBuyerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OrderViewModel @Inject constructor(
    private val createOrderUseCase: CreateOrderUseCase,
    private val getProductByIdAsBuyerUseCase: GetProductByIdAsBuyerUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<OrderUiState> = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> get() = _uiState

    fun createOrder(
        productId: Int,
        bidPrice: Int
    ) {
        _uiState.update {
            it.copy(
                orderState = CreateOrderState.Loading
            )
        }
        val productToBid = OrderRequest(
            productId = productId,
            bidPrice = bidPrice
        )
        viewModelScope.launch {
            when(val result = createOrderUseCase(productToBid)) {
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            orderState = CreateOrderState.Error(result.exception?.message.toString())
                        )
                    }
                }
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            orderState = CreateOrderState.Success
                        )
                    }
                }
            }
        }
    }

    fun getProductById(productId: Int) {
        viewModelScope.launch {
            when (val result = getProductByIdAsBuyerUseCase(productId)) {
                is Result.Error -> {
                    Log.d("getproduct", "getProductById: Error")
                }
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            product = result.data
                        )
                    }
                }
            }
        }
    }

}

data class OrderUiState(
    val product: Product? = null,
    val orderState: CreateOrderState = CreateOrderState.Idle
)

sealed interface CreateOrderState{
    object Idle : CreateOrderState
    object Loading : CreateOrderState
    object Success : CreateOrderState
    data class Error(val message: String) : CreateOrderState
}


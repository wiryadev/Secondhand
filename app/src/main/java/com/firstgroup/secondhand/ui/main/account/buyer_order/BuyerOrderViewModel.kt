package com.firstgroup.secondhand.ui.main.account.buyer_order

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Order
import com.firstgroup.secondhand.domain.order.CancelOrderUseCase
import com.firstgroup.secondhand.domain.order.GetOrderByIdAsBuyerUseCase
import com.firstgroup.secondhand.domain.order.GetOrdersAsBuyerUseCase
import com.firstgroup.secondhand.domain.order.UpdateOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyerOrderViewModel @Inject constructor(
    private val getOrdersAsBuyerUseCase: GetOrdersAsBuyerUseCase,
    private val getOrderByIdAsBuyerUseCase: GetOrderByIdAsBuyerUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<BuyerOrderUiState> = MutableStateFlow(
        BuyerOrderUiState()
    )
    val uiState: StateFlow<BuyerOrderUiState> get() = _uiState

    fun getOrder() {
        viewModelScope.launch {
            when (val result = getOrdersAsBuyerUseCase(Unit)) {
                is Result.Success -> {
                    Log.d("getOrder", "get Order Success: ${result.data} ")
                    _uiState.update {
                        it.copy(
                            orders = BuyerAllOrderState.Success(result.data)
                        )
                    }
                }
                is Result.Error -> {
                    Log.d("getOrder", "get Order Error: ${result.exception?.message.toString()}")
                    _uiState.update {
                        it.copy(
                            orders = BuyerAllOrderState.Error(result.exception?.message.toString())
                        )
                    }
                }
            }
        }
    }

    fun getOrderById(orderId: Int) {
        _uiState.update {
            it.copy(
                order = BuyerOrderState.Idle
            )
        }
        viewModelScope.launch {
            when (val result = getOrderByIdAsBuyerUseCase(orderId)) {
                is Result.Error -> {
                    Log.d("orderbyid", "getOrderByIderror: ${result.exception?.message}")
                }
                is Result.Success -> {
                    Log.d("orderbyid", "getOrderByIdsuccess: ${result.data}")
                    _uiState.update {
                        it.copy(
                            order = BuyerOrderState.Success(result.data)
                        )
                    }
                }
            }
        }
    }

    fun resetAlertDialogState(isDialogDismissed: Boolean) {
        if (isDialogDismissed) {
            _uiState.update {
                it.copy(
                    order = BuyerOrderState.Idle
                )
            }
        }
    }

    fun updateBidPrice(updateParam: UpdateOrderUseCase.Param) {
        viewModelScope.launch {
            when (val result = updateOrderUseCase(updateParam)) {
                is Result.Error -> {
                    Log.d("updateBidPrice", "updateBidPrice: ${result.exception?.message}")
                }
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            orders = BuyerAllOrderState.Loading,
                            message = "Bid Price Updated"
                        )
                    }
                    getOrder()
                    Log.d("updateBidPrice", "updateBidPrice: ${result.data}")
                }
            }
        }
    }

    fun cancelOrderById(orderId: Int) {
        viewModelScope.launch {
            when (val result = cancelOrderUseCase(orderId)) {
                is Result.Error -> {
                    Log.d("cancelorder", "cancelOrderById Error: ${result.exception?.message}")
                }
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            orders = BuyerAllOrderState.Loading,
                            message = result.data.message
                        )
                    }
                    getOrder()
                }
            }
        }
    }
}

data class BuyerOrderUiState(
    val orders: BuyerAllOrderState = BuyerAllOrderState.Loading,
    val order: BuyerOrderState = BuyerOrderState.Idle,
    val message: String? = null
)

sealed interface BuyerAllOrderState {
    data class Success(val ordersData: List<Order>) : BuyerAllOrderState
    data class Error(val error: String) : BuyerAllOrderState
    object Loading : BuyerAllOrderState
}

sealed interface BuyerOrderState {
    data class Success(val orderData: Order) : BuyerOrderState
    data class Error(val error: String) : BuyerOrderState
    object Idle : BuyerOrderState
}
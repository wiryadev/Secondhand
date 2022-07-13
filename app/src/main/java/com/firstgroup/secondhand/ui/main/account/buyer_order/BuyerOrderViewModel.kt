package com.firstgroup.secondhand.ui.main.account.buyer_order

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Order
import com.firstgroup.secondhand.domain.order.GetOrdersAsBuyerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyerOrderViewModel @Inject constructor(
    private val getOrdersAsBuyerUseCase: GetOrdersAsBuyerUseCase,
): ViewModel() {

    private val _uiState: MutableStateFlow<BuyerOrderUiState> = MutableStateFlow(
        BuyerOrderUiState()
    )
    val uiState: StateFlow<BuyerOrderUiState> get() = _uiState

    fun getOrder() {
        viewModelScope.launch {
            when(val result = getOrdersAsBuyerUseCase(Unit)) {
                is Result.Success -> {
                    Log.d("getOrder", "get Order Success: ${result.data} ")
                    _uiState.update {
                        it.copy(
                            orders = BuyerOrdersState.Success(result.data)
                        )
                    }
                }
                is Result.Error -> {
                    Log.d("getOrder", "get Order Error: ${result.exception?.message.toString()}")
                    _uiState.update {
                        it.copy(
                            orders = BuyerOrdersState.Error(result.exception?.message.toString())
                        )
                    }
                }
            }
        }
    }

}

data class BuyerOrderUiState(
    val orders: BuyerOrdersState = BuyerOrdersState.Loading
)

sealed interface BuyerOrdersState{
    data class Success(val ordersData: List<Order>): BuyerOrdersState
    data class Error(val error: String): BuyerOrdersState
    object Loading: BuyerOrdersState
}
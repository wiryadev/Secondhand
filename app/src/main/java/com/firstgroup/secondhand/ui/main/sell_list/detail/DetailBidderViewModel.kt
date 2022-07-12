package com.firstgroup.secondhand.ui.main.sell_list.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Order
import com.firstgroup.secondhand.domain.order.GetOrderByIdAsSellerUseCase
import com.firstgroup.secondhand.domain.order.RespondOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailBidderViewModel @Inject constructor(
    private val respondOrderUseCase: RespondOrderUseCase,
    private val getOrderByIdAsSellerUseCase: GetOrderByIdAsSellerUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<DetailBidderUiState> = MutableStateFlow(
        DetailBidderUiState()
    )
    val uiState: StateFlow<DetailBidderUiState> get() = _uiState

    fun getOrderAsSeller(orderId: Int) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val result = getOrderByIdAsSellerUseCase(orderId)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            order = result.data,
                            isLoading = false,
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

    fun respondOrder(orderId: Int, orderResponse: Boolean) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val result =
                respondOrderUseCase(RespondOrderUseCase.Param(orderId, orderResponse))) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.exception?.message.toString()
                        )
                    }
                }
            }
        }
    }
}

data class DetailBidderUiState(
    val order: Order? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
)
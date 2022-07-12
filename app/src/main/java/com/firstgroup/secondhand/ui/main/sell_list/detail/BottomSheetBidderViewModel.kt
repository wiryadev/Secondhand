package com.firstgroup.secondhand.ui.main.sell_list.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Order
import com.firstgroup.secondhand.domain.order.GetOrderByIdAsSellerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetBidderViewModel @Inject constructor(
    private val getOrderByIdAsSellerUseCase: GetOrderByIdAsSellerUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<BottomSheetBidderUiState> = MutableStateFlow(
        BottomSheetBidderUiState()
    )
    val uiState: StateFlow<BottomSheetBidderUiState> get() = _uiState

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
}

data class BottomSheetBidderUiState(
    val order: Order? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
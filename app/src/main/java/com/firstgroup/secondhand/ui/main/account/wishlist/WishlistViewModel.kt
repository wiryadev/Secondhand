package com.firstgroup.secondhand.ui.main.account.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Wishlist
import com.firstgroup.secondhand.domain.wishlist.GetWishlistUseCase
import com.firstgroup.secondhand.domain.wishlist.RemoveFromWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val getWishlistUseCase: GetWishlistUseCase,
    private val removeFromWishlistUseCase: RemoveFromWishlistUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<WishlistUiState> = MutableStateFlow(
        WishlistUiState()
    )
    val uiState: StateFlow<WishlistUiState> get() = _uiState

    fun getWishlist() {
        viewModelScope.launch {
            when(val result = getWishlistUseCase(Unit)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            wishlist = WishlistState.Success(result.data)
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            wishlist = WishlistState.Error(result.exception?.message.toString())
                        )
                    }
                }
            }
        }
    }

    fun removeFromWishlist(id: Int){
        viewModelScope.launch {
            when (val result = removeFromWishlistUseCase(id)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(isSuccess = true)
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

    fun resetWishlist(){
        _uiState.update {
            it.copy(
                wishlist = WishlistState.Loading
            )
        }
        getWishlist()
    }

}

data class WishlistUiState(
    val wishlist: WishlistState = WishlistState.Loading,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

sealed interface WishlistState{
    data class Success(val wishlist: List<Wishlist>): WishlistState
    data class Error(val error: String): WishlistState
    object Loading: WishlistState
}
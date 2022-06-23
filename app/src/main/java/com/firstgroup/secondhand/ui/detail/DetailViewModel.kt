package com.firstgroup.secondhand.ui.detail

import androidx.lifecycle.ViewModel
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.domain.auth.GetUserUseCase
import com.firstgroup.secondhand.domain.product.GetProductByIdAsBuyerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductByIdAsBuyerUseCase: GetProductByIdAsBuyerUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {


}

sealed interface SellerInfoUiState {
    data class Success(val user: User) : SellerInfoUiState
    object Error : SellerInfoUiState
    object Loading : SellerInfoUiState
}

sealed interface ProductsUiState {
    data class Success(val product: Product) : ProductsUiState
    object Error : ProductsUiState
    object Loading : ProductsUiState
}

data class DetailUiState(
    val sellerState: SellerInfoUiState,
    val productState: ProductsUiState,
)
package com.firstgroup.secondhand.ui.main.sell

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.core.network.product.model.ProductRequest
import com.firstgroup.secondhand.domain.auth.GetSessionUseCase
import com.firstgroup.secondhand.domain.auth.GetUserUseCase
import com.firstgroup.secondhand.domain.product.AddNewProductUseCase
import com.firstgroup.secondhand.domain.product.GetCategoriesUseCase
import com.firstgroup.secondhand.domain.product.RefreshCategoriesUseCase
import com.firstgroup.secondhand.ui.auth.LoginState
import com.firstgroup.secondhand.ui.main.home.CategoriesUiState
import com.firstgroup.secondhand.ui.main.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SellViewModel @Inject constructor(
    private val getSessionUseCase: GetSessionUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val refreshCategoriesUseCase: RefreshCategoriesUseCase,
    private val addNewProductUseCase: AddNewProductUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val defaultCategory = listOf(
        Category(id = HomeViewModel.DEFAULT_SELECTED_CATEGORY_ID, name = "Pick Category"),
    )

    private val _uiState: MutableStateFlow<SellUiState> = MutableStateFlow(
        SellUiState(
            selectedCategoryId = defaultCategory[0]
        )
    )
    val uiState: StateFlow<SellUiState> get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCategories()
        }
    }

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

    private suspend fun getCategories() {
        refreshCategoriesUseCase(Unit)
        when (val result = getCategoriesUseCase(Unit)) {
            is Result.Error -> {
                _uiState.update {
                    it.copy(categoryState = CategoriesUiState.Error)
                }
            }
            is Result.Success -> {
                _uiState.update {
                    it.copy(
                        categoryState = CategoriesUiState.Success(
                            categories = result.data
                        )
                    )
                }
            }
        }
    }

    fun setCategory(category: Category) {
        _uiState.update {
            it.copy(
                selectedCategoryId = category
            )
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
                        it.copy(error = result.exception?.message.toString())
                    }
                }
            }
        }
    }

    fun setImage(imageFile: File) {
        _uiState.update {
            it.copy(image = imageFile)
        }
    }

    fun postProduct(product: ProductRequest?) {
        _uiState.update {
            it.copy(
                postProductState = PostProductState.Loading
            )
        }
        viewModelScope.launch {
            if (product != null) {
                when (val result = addNewProductUseCase(product)) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                postProductState = PostProductState.Success
                            )
                        }
                    }
                    is Result.Error -> {
                        Log.d("addproduct", result.exception?.message.toString())
                        // show snack bar error
                        _uiState.update {
                            it.copy(
                                postProductState = PostProductState.Error(
                                    message = result.exception?.message.toString()
                                )
                            )
                        }
                    }
                }
            } else {
                _uiState.update {
                    it.copy(
                        postProductState = PostProductState.Error(
                            message = "Fill all necessary data first"
                        )
                    )
                }
            }
        }
    }

    fun addProduct(
        name: String,
        description: String,
        basePrice: String
    ) {
        val image = uiState.value.image
        val city = uiState.value.recentUser?.city

        when {
            image == null -> {
                _uiState.update {
                    it.copy(
                        error = "Please pick image first"
                    )
                }
            }
            city == null -> {
                _uiState.update {
                    it.copy(
                        error = "Please complete your profile"
                    )
                }
            }
            else -> {
                val productData = ProductRequest(
                    name = name,
                    description = description,
                    basePrice = basePrice.toInt(),
                    categoryIds = listOf(uiState.value.selectedCategoryId.id),
                    location = city,
                    image = image,
                )

                _uiState.update {
                    it.copy(
                        productData = productData
                    )
                }
            }
        }
    }

    fun showPreviewScreen() {
        _uiState.update {
            it.copy(
                sellState = SellState.PreviewNewProduct
            )
        }
    }

    fun showAddProductScreen() {
        _uiState.update {
            it.copy(
                sellState = SellState.AddNewProduct
            )
        }
    }
}

data class SellUiState(
    val productData: ProductRequest? = null,
    val image: File? = null,
    val loginState: LoginState = LoginState.Idle,
    val recentUser: User? = null,
    val error: String? = null,
    val categoryState: CategoriesUiState = CategoriesUiState.Loading,
    val selectedCategoryId: Category,
    val sellState: SellState = SellState.AddNewProduct,
    val postProductState: PostProductState = PostProductState.Idle
)

sealed interface SellState {
    object AddNewProduct : SellState
    object PreviewNewProduct : SellState
}

sealed interface PostProductState {
    object Idle : PostProductState
    object Loading : PostProductState
    object Success : PostProductState
    data class Error(val message: String) : PostProductState
}
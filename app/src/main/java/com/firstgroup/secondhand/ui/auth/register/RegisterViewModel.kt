package com.firstgroup.secondhand.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
import com.firstgroup.secondhand.domain.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> get() = _uiState.asStateFlow()

    fun register(
        name: String,
        email: String,
        password: String,
        phoneNumber: String,
        address: String
    ) {
        _uiState.update { uiState ->
            uiState.copy(isLoading = true)
        }
        val userRequest = AuthUserRequest(
            fullName = name,
            email = email,
            password = password,
            phoneNo = phoneNumber,
            address = address,
            image = null
        )
        viewModelScope.launch {
            when (val result = registerUseCase(userRequest)) {
                is Result.Success -> {
                    _uiState.update { uiState ->
                        uiState.copy(isLoading = false, isSuccess = true)
                    }
                }
                is Result.Error -> {
                    val exceptionMessage = result.exception?.message.toString()
                    val error = when {
                        exceptionMessage.contains("400") -> {
                            R.string.email_exist_error
                        }
                        exceptionMessage.contains("500") -> {
                            R.string.fill_all_field_error
                        }
                        else -> {
                            R.string.unknown_error
                        }
                    }
                    _uiState.update { uiState ->
                        uiState.copy(
                            isLoading = false,
                            errorMessage = error,
                        )
                    }
                }
            }
        }
    }

    fun resetErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

data class RegisterUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: Int? = null,
)
package com.firstgroup.secondhand.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.network.auth.model.LoginRequest
import com.firstgroup.secondhand.domain.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> get() = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        _uiState.update { uiState ->
            uiState.copy(isLoading = true)
        }
        val loginCredential = LoginRequest(
            email = email, password = password
        )
        viewModelScope.launch {
            when (val result = loginUseCase(loginCredential)) {
                is Result.Success -> {
                    _uiState.update { uiState ->
                        uiState.copy(isLoading = false, isSuccess = true)
                    }
                }
                is Result.Error -> {
                    val exceptionMessage = result.exception?.message.toString()
                    _uiState.update { uiState ->
                        uiState.copy(
                            isLoading = false,
                            errorMessage = exceptionMessage,
                        )
                    }
                }
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState()
    }
}

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
)
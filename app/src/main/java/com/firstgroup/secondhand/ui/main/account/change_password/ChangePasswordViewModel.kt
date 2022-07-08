package com.firstgroup.secondhand.ui.main.account.change_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.network.auth.model.ChangePasswordRequest
import com.firstgroup.secondhand.domain.auth.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ChangePasswordUiState> =
        MutableStateFlow(ChangePasswordUiState())
    val uiState: StateFlow<ChangePasswordUiState> get() = _uiState.asStateFlow()

    fun updatePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String,
    ) {
        _uiState.update { uiState ->
            uiState.copy(isLoading = true)
        }
        val changePassword = ChangePasswordRequest(
            currentPassword = currentPassword,
            newPassword = newPassword,
            confirmationPassword = confirmPassword
        )
        viewModelScope.launch {
            when (val result = changePasswordUseCase(changePassword)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(isSuccess = true, isLoading = false)
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            errorMessage = result.exception?.message.toString(),
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }

    fun resetState() {
        _uiState.update {
            it.copy(
                isLoading = false,
                isSuccess = false,
                errorMessage = null
            )
        }
    }
}

data class ChangePasswordUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
)
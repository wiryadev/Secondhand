package com.firstgroup.secondhand.ui.auth.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.common.result.asResult
import com.firstgroup.secondhand.core.data.repositories.auth.AuthRepository
import com.firstgroup.secondhand.core.network.auth.model.AuthUserRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState())
    val uiState: LiveData<RegisterUiState> get() = _uiState.asLiveData()

    fun register(
        name: String,
        email: String,
        password: String,
        phoneNumber: String,
        address: String
    ) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            authRepository.register(
                AuthUserRequest(
                    fullName = name,
                    email = email,
                    password = password,
                    phoneNo = phoneNumber,
                    address = address,
                    image = null
                )
            ).asResult().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update { uiState ->
                            uiState.copy(isLoading = false, isSuccess = true)
                        }
                    }
                    is Result.Error -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                isLoading = false, errorMessage = result.toString()
                            )
                        }
                    }
                    else -> {
//                        Log.d("errorregister", "register: $result")
                    }
                }
            }
        }
    }
}

data class RegisterUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
)
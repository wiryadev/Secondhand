package com.firstgroup.secondhand

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.domain.auth.GetSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getSessionUseCase: GetSessionUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainUiState> =
        MutableStateFlow(MainUiState.Initial)
    val uiState: StateFlow<MainUiState> get() = _uiState.asStateFlow()

    fun checkSession() {
        viewModelScope.launch {
            getSessionUseCase(Unit).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        Log.d("Authentication", "getUser: ${result.exception}")
                        _uiState.value = MainUiState.Loaded(
                            isLoggedIn = false
                        )
                    }
                    is Result.Success -> {
                        _uiState.value = MainUiState.Loaded(
                            isLoggedIn = result.data.token.isNotEmpty()
                        )
                    }
                }
            }
        }
    }
}

sealed class MainUiState {
    object Initial : MainUiState()
    data class Loaded(val isLoggedIn: Boolean) : MainUiState()
}
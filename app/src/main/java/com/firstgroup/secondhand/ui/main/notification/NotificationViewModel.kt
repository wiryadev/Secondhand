package com.firstgroup.secondhand.ui.main.notification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Notification
import com.firstgroup.secondhand.domain.auth.GetSessionUseCase
import com.firstgroup.secondhand.domain.notification.GetNotificationByIdUseCase
import com.firstgroup.secondhand.domain.notification.GetNotificationsUseCase
import com.firstgroup.secondhand.ui.auth.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val getNotificationByIdUseCase: GetNotificationByIdUseCase,
    private val getSessionUseCase: GetSessionUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<NotificationUiState> = MutableStateFlow(
        NotificationUiState()
    )
    val uiState: StateFlow<NotificationUiState> get() = _uiState

    fun getSession(){
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

    fun getNotification() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val result = getNotificationsUseCase(Unit)) {
                is Result.Success -> {
                    Log.d("datanotifikasi", result.data.toString())
                    _uiState.update {
                        it.copy(
                            notifications = result.data,
                            isLoading = false
                        )
                    }
                }
                is Result.Error -> {
                    Log.d("datanotifikasi", result.exception?.message.toString())
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

data class NotificationUiState(
    val notifications: List<Notification>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val loginState: LoginState = LoginState.Idle,
)

//sealed interface NotificationState{
//    data class Success(val products: List<Notification>) : NotificationState
//    data class Error(val message: String) : NotificationState
//    object Idle : NotificationState
//}
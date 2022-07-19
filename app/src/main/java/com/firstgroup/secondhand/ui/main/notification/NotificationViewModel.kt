package com.firstgroup.secondhand.ui.main.notification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.model.Notification
import com.firstgroup.secondhand.domain.auth.GetSessionUseCase
import com.firstgroup.secondhand.domain.notification.GetNotificationByIdUseCase
import com.firstgroup.secondhand.domain.notification.GetNotificationsUseCase
import com.firstgroup.secondhand.domain.notification.UpdateNotificationUseCase
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
    private val getSessionUseCase: GetSessionUseCase,
    private val updateNotificationUseCase: UpdateNotificationUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<NotificationUiState> = MutableStateFlow(
        NotificationUiState()
    )
    val uiState: StateFlow<NotificationUiState> get() = _uiState

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

    fun getNotifications() {
        viewModelScope.launch {
            when (val result = getNotificationsUseCase(Unit)) {
                is Result.Success -> {
                    Log.d("datanotifikasi", result.data.toString())
                    _uiState.update {
                        it.copy(
                            notifications = AllNotificationState.Success(result.data)
                        )
                    }
                }
                is Result.Error -> {
                    Log.d("datanotifikasi", result.exception?.message.toString())
                    _uiState.update {
                        it.copy(
                            notifications = AllNotificationState.Error(result.exception?.message.toString())
                        )
                    }
                }
            }
        }
    }

    fun getNotificationById(notificationId: Int) {
        viewModelScope.launch {
            when (val result = getNotificationByIdUseCase(notificationId)) {
                is Result.Error -> {
                    Log.d("notificationbyid", "getNotificationById: ${result.exception?.message}")
                }
                is Result.Success -> {
                    Log.d("notificationbyid", "getNotificationById: ${result.data}")
                    _uiState.update {
                        it.copy(
                            notification = NotificationState.Success(result.data)
                        )
                    }
                }
            }
            when (val result = updateNotificationUseCase(notificationId)) {
                is Result.Error -> {
                    Log.d("updatenotif", "updateNotificationStatus: ${result.exception?.message}")
                }
                is Result.Success -> {
                    Log.d("updatenotif", "updateNotificationStatus: ${result.data}")

                }
            }
        }
    }

    fun resetNotificationState(isDialogDismissed: Boolean) {
        if (isDialogDismissed) {
            _uiState.update {
                it.copy(
                    notification = NotificationState.Idle,
                    notifications = AllNotificationState.Loading
                )
            }
            getNotifications()
        }
    }
}

data class NotificationUiState(
    val notifications: AllNotificationState = AllNotificationState.Loading,
    val loginState: LoginState = LoginState.Idle,
    val notification: NotificationState = NotificationState.Idle,
)

sealed interface AllNotificationState {
    data class Success(val data: List<Notification>) : AllNotificationState
    data class Error(val message: String) : AllNotificationState
    object Loading : AllNotificationState
}

sealed interface NotificationState {
    data class Success(val notification: Notification) : NotificationState
    data class Error(val message: String) : NotificationState
    object Idle : NotificationState
}
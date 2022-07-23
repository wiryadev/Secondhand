package com.firstgroup.secondhand.ui.main.notification

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.ui.auth.AuthActivity
import com.firstgroup.secondhand.ui.auth.LoginState
import com.firstgroup.secondhand.ui.components.*
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private val viewModel: NotificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsState()
                MdcTheme {
                    NotificationScreen(
                        viewModel = viewModel,
                        uiState = uiState,
                        onLoginClick = ::goToLoginScreen
                    )
                }
            }
        }
    }

    private fun goToLoginScreen() {
        startActivity(Intent(requireContext(), AuthActivity::class.java))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSession()
    }
}


@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel,
    uiState: NotificationUiState,
    onLoginClick: () -> Unit,
) {
    LaunchedEffect(key1 = uiState.loginState) {
        if (uiState.loginState is LoginState.Loaded) {
            if (uiState.loginState.isLoggedIn) {
                viewModel.getNotifications()
            }
        }

    }

    when (uiState.loginState) {
        is LoginState.Idle -> {
            GenericLoadingScreen()
        }
        is LoginState.Loaded -> {
            if (uiState.loginState.isLoggedIn) {
                NotificationScreen(
                    uiState = uiState,
                    onNotificationClick = viewModel::getNotificationById,
                    onDialogDismiss = viewModel::resetNotificationState
                )
            } else {
                LoginLayoutPlaceholder(onButtonClick = onLoginClick)
            }
        }
    }
}

@Composable
fun NotificationScreen(
    uiState: NotificationUiState,
    onNotificationClick: (Int) -> Unit,
    onDialogDismiss: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.notification),
            style = MaterialTheme.typography.h5
        )
        when (uiState.notifications) {
            is AllNotificationState.Error -> {

            }
            is AllNotificationState.Loading -> {
                GenericLoadingScreen()
            }
            is AllNotificationState.Success -> {
                if (uiState.notifications.data.isEmpty()) {
                    OrderLayoutPlaceholder(message = "Notification Empty")
                } else {
                    NotificationList(
                        notifications = uiState.notifications.data,
                        onNotificationClick = onNotificationClick
                    )
                }
            }
        }
        when (uiState.notification) {
            is NotificationState.Idle -> {

            }
            is NotificationState.Error -> {

            }
            is NotificationState.Success -> {
                NotificationDetails(
                    notificationDetails = uiState.notification.notification,
                    resetStateOnDialogDismiss = onDialogDismiss,
                )
            }
        }
    }
}
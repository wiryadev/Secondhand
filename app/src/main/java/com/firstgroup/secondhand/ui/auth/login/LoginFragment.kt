package com.firstgroup.secondhand.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.ui.components.PrimaryButton
import com.firstgroup.secondhand.ui.components.TopSnackBar
import com.firstgroup.secondhand.utils.TestTag
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    LoginScreen(
                        viewModel = viewModel,
                        toRegister = {
                            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                        },
                        onLoginSuccess = {
                            activity?.finish()
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    toRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LoginScreen(
        uiState = uiState,
        onSnackbarDismissed = viewModel::resetState,
        onLoginClick = viewModel::login,
        onLoginSuccess = onLoginSuccess,
        toRegister = toRegister
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onLoginClick: (String, String) -> Unit,
    onLoginSuccess: () -> Unit,
    onSnackbarDismissed: () -> Unit,
    toRegister: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
        ) {
            val focusManager = LocalFocusManager.current
            Spacer(modifier = Modifier.height(72.dp))
            // Login Title
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTag.LOGIN_TITLE)
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Login Email Field
            Text(
                text = stringResource(id = R.string.email),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp, colorResource(id = R.color.neutral_02), RoundedCornerShape(16.dp)
                    )
                    .testTag(TestTag.EMAIL_TEXT_FIELD),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onDone = { focusManager.moveFocus(FocusDirection.Down) }),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_email),
                        style = MaterialTheme.typography.body1,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = colorResource(id = R.color.neutral_02)
                ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Login Password Field
            var passwordVisible by rememberSaveable { mutableStateOf(false) }
            Text(
                text = stringResource(R.string.password),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp, colorResource(id = R.color.neutral_02), RoundedCornerShape(16.dp)
                    )
                    .testTag(TestTag.PASSWORD_TEXT_FIELD),
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                trailingIcon = {
                    val description = if (passwordVisible) {
                        stringResource(R.string.desc_hide_password)
                    } else {
                        stringResource(R.string.desc_show_password)
                    }
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier.testTag(TestTag.PASSWORD_VISIBILITY_TOGGLE)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                id = if (passwordVisible) {
                                    R.drawable.ic_show_password
                                } else {
                                    R.drawable.ic_hide_password
                                }
                            ),
                            contentDescription = description,
                        )
                    }
                },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_password),
                        style = MaterialTheme.typography.body1,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = colorResource(id = R.color.neutral_02)
                ),
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Login Button
            PrimaryButton(
                onClick = { onLoginClick(email, password) },
                content = {
                    Text(
                        text = if (uiState.isLoading) {
                            stringResource(id = R.string.loading)
                        } else {
                            stringResource(id = R.string.login)
                        },
                    )
                },
                enabled = !uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTag.LOGIN_BUTTON),
            )
            // Login Bottom Clickable Text
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.account_question1),
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.not_have_account),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.dark_blue_04),
                    modifier = Modifier.clickable { toRegister() })
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        uiState.errorMessage?.let { errorMessage ->
            val message = when {
                errorMessage.contains("401") -> {
                    R.string.invalid_email_password
                }
                errorMessage.contains("500") -> {
                    R.string.internal_service_error
                }
                else -> {
                    R.string.unknown_error
                }
            }
            TopSnackBar(
                message = stringResource(id = message),
                isError = true,
                onDismissClick = onSnackbarDismissed,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        if (uiState.isSuccess) {
            TopSnackBar(message = stringResource(id = R.string.login_success),
                isError = false,
                onDismissClick = {
                    onSnackbarDismissed()
                    onLoginSuccess()
                })
        }
    }
}


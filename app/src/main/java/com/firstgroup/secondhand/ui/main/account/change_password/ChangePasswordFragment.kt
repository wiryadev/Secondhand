package com.firstgroup.secondhand.ui.main.account.change_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.ui.components.PrimaryButton
import com.firstgroup.secondhand.ui.components.TopSnackBar
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment: Fragment() {

    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    ChangePasswordScreen(
                        viewModel = viewModel,
                        backToMainAccountPage = ::backToAccountScreen
                    )
                }
            }
        }
    }

    private fun backToAccountScreen(){
        findNavController().navigate(
            ChangePasswordFragmentDirections.actionChangePasswordFragmentToMainNavigationAccount()
        )
    }
}

@Composable
fun ChangePasswordScreen(
    viewModel: ChangePasswordViewModel,
    backToMainAccountPage: () -> Unit
){
    val uiState by viewModel.uiState.collectAsState()
    ChangePasswordScreen(
        onChangeClick = viewModel::updatePassword,
        uiState = uiState,
        onSnackbarDismissed = viewModel::resetState,
        backToMainAccountPage = backToMainAccountPage
    )
}


@Composable
fun ChangePasswordScreen(
    onChangeClick: (String, String, String) -> Unit,
    uiState: ChangePasswordUiState,
    onSnackbarDismissed: () -> Unit,
    backToMainAccountPage: () -> Unit
){

    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            val focusManager = LocalFocusManager.current
            var currentPasswordVisible by rememberSaveable { mutableStateOf(false) }
            var newPasswordVisible by rememberSaveable { mutableStateOf(false) }
            var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }
            Spacer(modifier = Modifier.height(72.dp))
            /* TODO = halaman form disamain aja judul diatas kyak edit account sama jual barang + masih bahasa indonesia */
            Text(
                text = stringResource(R.string.change_password),
                style = MaterialTheme.typography.h4
            )
            // old password
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.old_password),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = currentPassword,
                onValueChange = { currentPassword = it },
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
                visualTransformation = if (currentPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                trailingIcon = {
                    val description =
                        if (currentPasswordVisible) {
                            stringResource(R.string.desc_hide_password)
                        } else {
                            stringResource(R.string.desc_show_password)
                        }
                    if (currentPasswordVisible) {
                        IconButton(onClick = { currentPasswordVisible = !currentPasswordVisible }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = R.drawable.ic_show_password
                                ),
                                description
                            )
                        }
                    } else {
                        IconButton(onClick = { currentPasswordVisible = !currentPasswordVisible }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = R.drawable.ic_hide_password
                                ),
                                description
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_old_password),
                        style = MaterialTheme.typography.body1,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = colorResource(id = R.color.neutral_02)
                ),
            )
            // new password
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.new_password),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
                visualTransformation = if (newPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                trailingIcon = {
                    val description =
                        if (newPasswordVisible) {
                            stringResource(R.string.desc_hide_password)
                        } else {
                            stringResource(R.string.desc_show_password)
                        }
                    if (newPasswordVisible) {
                        IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = R.drawable.ic_show_password
                                ),
                                description
                            )
                        }
                    } else {
                        IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = R.drawable.ic_hide_password
                                ),
                                description
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_new_password),
                        style = MaterialTheme.typography.body1,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = colorResource(id = R.color.neutral_02)
                ),
            )
            // confirm new password
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.confirm_password),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
                visualTransformation = if (confirmPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                trailingIcon = {
                    val description =
                        if (confirmPasswordVisible) {
                            stringResource(R.string.desc_hide_password)
                        } else {
                            stringResource(R.string.desc_show_password)
                        }
                    if (confirmPasswordVisible) {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = R.drawable.ic_show_password
                                ),
                                description
                            )
                        }
                    } else {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = R.drawable.ic_hide_password
                                ),
                                description
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_confirm_password),
                        style = MaterialTheme.typography.body1,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = colorResource(id = R.color.neutral_02)
                ),
            )
            // button
            Spacer(modifier = Modifier.height(24.dp))
            /* TODO = abis ubah password nampil snackbar tapi diem ditempat dan form form masih ada isinya */
            PrimaryButton(
                onClick = { onChangeClick(currentPassword, newPassword, confirmPassword) },
                content = {
                    Text(
                        text = if (uiState.isLoading) {
                            stringResource(id = R.string.loading)
                        } else {
                            stringResource(R.string.change)
                        },
                    )
                },
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            )
        }
        IconButton(
            onClick = { backToMainAccountPage() },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_left),
                contentDescription = stringResource(id = R.string.description_back_button)
            )
        }
        uiState.errorMessage?.let { errorMessage ->
            val message = when {
                errorMessage.contains("400") -> {
                    R.string.invalid_password
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
            TopSnackBar(
                message = stringResource(R.string.change_password_success),
                isError = false,
                onDismissClick = {
                    onSnackbarDismissed()
                }
            )
        }
    }
}
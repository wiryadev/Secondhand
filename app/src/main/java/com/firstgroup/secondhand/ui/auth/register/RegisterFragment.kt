package com.firstgroup.secondhand.ui.auth.register

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
import com.firstgroup.secondhand.ui.components.TopSnackBar
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    RegisterScreen(
                        viewModel = viewModel,
                        toLogin = {
                            findNavController().navigate(
                                R.id.action_registerFragment_to_loginFragment
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    toLogin: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    RegisterScreen(
        uiState = uiState,
        onRegisterClick = viewModel::register,
        onSnackbarDismissed = viewModel::resetState,
        toLogin = toLogin
    )
}

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onRegisterClick: (String, String, String, String, String) -> Unit,
    onSnackbarDismissed: () -> Unit,
    toLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
                .padding(top = 48.dp)
        ) {
            val focusManager = LocalFocusManager.current
            // Register Title
            Text(
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 12.dp)
            )

            // Name Field
            Text(
                text = stringResource(R.string.name),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 4.dp, top = 12.dp)
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_name),
                        style = MaterialTheme.typography.body1,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = colorResource(id = R.color.neutral_02)
                )
            )
            // Email Field
            Text(
                text = stringResource(R.string.email),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 4.dp, top = 8.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
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
            // Phone Number Field
            Text(
                text = stringResource(R.string.phone_number),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 4.dp, top = 8.dp)

            )
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_phone_number),
                        style = MaterialTheme.typography.body1,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = colorResource(id = R.color.neutral_02)
                )
            )
            // Address Field
            Text(
                text = stringResource(R.string.address),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 4.dp, top = 8.dp)
            )
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_address),
                        style = MaterialTheme.typography.body1,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = colorResource(id = R.color.neutral_02)
                )
            )
            // Password Field
            var passwordVisible by rememberSaveable { mutableStateOf(false) }
            Text(
                text = stringResource(R.string.password),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 4.dp, top = 4.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                trailingIcon = {
                    val description =
                        if (passwordVisible) {
                            stringResource(R.string.desc_hide_password)
                        } else {
                            stringResource(R.string.desc_show_password)
                        }
                    if (passwordVisible) {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = R.drawable.ic_show_password
                                ),
                                description
                            )
                        }
                    } else {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
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
                        text = stringResource(R.string.placeholder_password),
                        style = MaterialTheme.typography.body1,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = colorResource(id = R.color.neutral_02)
                )
            )
            //Register Button
            Button(
                onClick = {
                    onRegisterClick(name, email, password, phoneNumber, address)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .size(height = 48.dp, width = 0.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = !uiState.isLoading
            ) {
                Text(
                    text =
                    if (uiState.isLoading) {
                        stringResource(id = R.string.loading)
                    } else {
                        stringResource(id = R.string.register)
                    },
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.account_question),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = stringResource(R.string.have_account),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.dark_blue_04),
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable { toLogin() }
                )
            }
        }

        IconButton(onClick = { toLogin() }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_left),
                stringResource(R.string.description_backTo_login)
            )
        }

        uiState.errorMessage?.let { errorMessage ->
            val message = when {
                errorMessage.contains("400") -> {
                    R.string.email_exist_error
                }
                errorMessage.contains("500") -> {
                    R.string.fill_all_field_error
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
                message = stringResource(id = R.string.register_succes),
                isError = false,
                onDismissClick = {
                    onSnackbarDismissed()
                    toLogin()
                }
            )
        }
    }
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun RegisterPreview() {
//    MdcTheme {
////        RegisterScreen(onLoginClick = { _, _, _, _, _ -> })
//    }
//}
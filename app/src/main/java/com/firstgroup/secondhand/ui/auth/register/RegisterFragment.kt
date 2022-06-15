package com.firstgroup.secondhand.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
                    RegisterScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(viewModel: RegisterViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    RegisterScreen(
        uiState = uiState,
        onLoginClick = { name, email, password, phoneNumber, address ->
            viewModel.register(name, email, password, phoneNumber, address)
        },
        onSnackbarDismissed = {
            viewModel.resetState()
        }
    )
}

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onLoginClick: (String, String, String, String, String) -> Unit,
    onSnackbarDismissed: () -> Unit,
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
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Daftar",
                fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 53.dp, bottom = 12.dp),
            )
            // Name Field
            Text(
                text = "Name",
                modifier = Modifier.padding(bottom = 4.dp, top = 12.dp),
                fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                maxLines = 1,
                placeholder = {
                    Text(
                        text = "Your Name",
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
                text = "Email", modifier = Modifier.padding(bottom = 4.dp, top = 8.dp),
                fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                maxLines = 1,
                placeholder = {
                    Text(
                        text = "Your Email",
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
                text = "Phone Number", modifier = Modifier.padding(bottom = 4.dp, top = 8.dp),
                style = MaterialTheme.typography.body1
            )
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                maxLines = 1,
                placeholder = {
                    Text(
                        text = "Your Number",
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
                text = "Address", modifier = Modifier.padding(bottom = 4.dp, top = 8.dp),
                style = MaterialTheme.typography.body1,
            )
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                maxLines = 1,
                placeholder = {
                    Text(
                        text = "Your Address",
                        style = MaterialTheme.typography.body1,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = colorResource(id = R.color.neutral_02)
                )
            )
            // Password Field
            Text(
                text = "Password", modifier = Modifier.padding(bottom = 12.dp, top = 4.dp),
                fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
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
                shape = RoundedCornerShape(16.dp),
                maxLines = 1,
                placeholder = {
                    Text(
                        text = "Your Password",
                        style = MaterialTheme.typography.body1,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = colorResource(id = R.color.neutral_02)
                )
            )
            Button(
                onClick = {
                    onLoginClick(name, email, password, phoneNumber, address)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = !uiState.isLoading
            ) {
                Text(
                    text = if (uiState.isLoading) "Loading" else "Register",
                )
            }
        }

        uiState.errorMessage?.let {
            TopSnackBar(
                message = stringResource(id = it),
                isError = true,
                onDismissClick = onSnackbarDismissed,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        if (uiState.isSuccess) {
            TopSnackBar(
                message = stringResource(id = R.string.register_succes),
                isError = false,
                onDismissClick = onSnackbarDismissed,
            )
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterPreview() {
    MdcTheme {
//        RegisterScreen(onLoginClick = { _, _, _, _, _ -> })
    }
}
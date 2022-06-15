package com.firstgroup.secondhand.ui.auth.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.firstgroup.secondhand.R
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
    RegisterScreen(onLoginClick = { name, email, password, phoneNumber, address ->
        viewModel.register(name, email, password, phoneNumber, address)
    })
}

@Composable
fun RegisterScreen(onLoginClick: (String, String, String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
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
            textStyle = TextStyle(
                fontFamily = FontFamily(
                    Font(
                        resId = R.font.poppins_regular,
                        style = FontStyle(R.style.TextAppearance_Material3_BodyMedium_SemiBold)
                    )
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .border(1.dp, colorResource(id = R.color.neutral_02), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            placeholder = {
                Text(
                    text = "Your Name",
                    fontFamily = FontFamily(
                        Font(
                            resId = R.font.poppins_regular,
                            style = FontStyle(R.style.TextAppearance_Material3_BodyMedium)
                        )
                    )
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
            textStyle = TextStyle(
                fontFamily = FontFamily(
                    Font(
                        resId = R.font.poppins_regular,
                        style = FontStyle(R.style.TextAppearance_Material3_BodyMedium_SemiBold)
                    )
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .border(1.dp, colorResource(id = R.color.neutral_02), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            placeholder = {
                Text(
                    text = "Your Email",
                    fontFamily = FontFamily(
                        Font(
                            resId = R.font.poppins_regular,
                            style = FontStyle(R.style.TextAppearance_Material3_BodyMedium)
                        )
                    )
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
            fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
        )
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            textStyle = TextStyle(
                fontFamily = FontFamily(
                    Font(
                        resId = R.font.poppins_regular,
                        style = FontStyle(R.style.TextAppearance_Material3_BodyMedium_SemiBold)
                    )
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .border(1.dp, colorResource(id = R.color.neutral_02), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            placeholder = {
                Text(
                    text = "Your Number",
                    fontFamily = FontFamily(
                        Font(
                            resId = R.font.poppins_regular,
                            style = FontStyle(R.style.TextAppearance_Material3_BodyMedium)
                        )
                    )
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
            fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
        )
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            textStyle = TextStyle(
                fontFamily = FontFamily(
                    Font(
                        resId = R.font.poppins_regular,
                        style = FontStyle(R.style.TextAppearance_Material3_BodyMedium_SemiBold)
                    )
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .border(1.dp, colorResource(id = R.color.neutral_02), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            placeholder = {
                Text(
                    text = "Your Address",
                    fontFamily = FontFamily(
                        Font(
                            resId = R.font.poppins_regular,
                            style = FontStyle(R.style.TextAppearance_Material3_BodyMedium)
                        )
                    )
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
            textStyle = TextStyle(
                fontFamily = FontFamily(
                    Font(
                        resId = R.font.poppins_regular,
                        style = FontStyle(R.style.TextAppearance_Material3_BodyMedium_SemiBold)
                    )
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .border(1.dp, colorResource(id = R.color.neutral_02), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            placeholder = {
                Text(
                    text = "Your Password",
                    fontFamily = FontFamily(
                        Font(
                            resId = R.font.poppins_regular,
                            style = FontStyle(R.style.TextAppearance_Material3_BodyMedium)
                        )
                    )
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                placeholderColor = colorResource(id = R.color.neutral_02)
            )
        ) // password
        Button(
            onClick = {
                Log.d("view model register", "RegisterScreen: ")
                onLoginClick(name, email, password, phoneNumber, address)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Daftar", fontSize = 14.sp,
                fontFamily = FontFamily(
                    Font(
                        resId = R.font.poppins_regular,
                        style = FontStyle(R.style.TextAppearance_Material3_BodyMedium)
                    )
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterPreview() {
    MdcTheme {
        RegisterScreen(onLoginClick = { _, _, _, _, _ -> })
    }
}
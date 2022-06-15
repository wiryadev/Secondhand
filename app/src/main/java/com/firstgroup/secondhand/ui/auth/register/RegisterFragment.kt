package com.firstgroup.secondhand.ui.auth.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
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
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 106.dp, bottom = 12.dp),
        )
        Text(text = "Name", modifier = Modifier.padding(bottom = 4.dp, top = 12.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .border(1.dp, colorResource(id = R.color.neutral_02), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            placeholder = { Text(text = "Your Name") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                placeholderColor = colorResource(id = R.color.neutral_02)
            )
        )
        Text(text = "Email", modifier = Modifier.padding(bottom = 4.dp, top = 8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .border(1.dp, colorResource(id = R.color.neutral_02), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            placeholder = { Text(text = "Your Email") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                placeholderColor = colorResource(id = R.color.neutral_02)
            ),
        ) // email
        Text(text = "Phone Number", modifier = Modifier.padding(bottom = 4.dp, top = 8.dp))
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .border(1.dp, colorResource(id = R.color.neutral_02), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            placeholder = { Text(text = "Your Number") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                placeholderColor = colorResource(id = R.color.neutral_02)
            )
        ) // phone number
        Text(text = "Address", modifier = Modifier.padding(bottom = 4.dp, top = 8.dp))
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .border(1.dp, colorResource(id = R.color.neutral_02), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            placeholder = { Text(text = "Your Address") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                placeholderColor = colorResource(id = R.color.neutral_02)
            )
        ) // address
        Text(text = "Password", modifier = Modifier.padding(bottom = 12.dp, top = 4.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .border(1.dp, colorResource(id = R.color.neutral_02), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            placeholder = { Text(text = "Your Password") },
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
            Text(text = "Daftar")
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
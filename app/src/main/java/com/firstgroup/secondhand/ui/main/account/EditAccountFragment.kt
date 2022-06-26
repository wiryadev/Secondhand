package com.firstgroup.secondhand.ui.main.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.ui.components.PrimaryButton
import com.google.android.material.composethemeadapter.MdcTheme

class EditAccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    EditAccountScreen()
                }
            }
        }
    }
}

@Composable
fun EditAccountScreen() {
    var name by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            // Back and Title
            Text(
                text = stringResource(R.string.account),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(40.dp))
            // Image
            Image(
                painter = painterResource(id = R.drawable.img_profile_placeholder),
                contentDescription = stringResource(R.string.description_profile_image),
                modifier = Modifier
                    .size(96.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))
            //Name
            Text(
                text = stringResource(R.string.name_required),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
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
            Spacer(modifier = Modifier.height(16.dp))
            // City
            Text(
                text = stringResource(R.string.city_required),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        colorResource(id = R.color.neutral_02),
                        RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.placeholder_city),
                        style = MaterialTheme.typography.body1,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    placeholderColor = colorResource(id = R.color.neutral_02)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Address
            Text(
                text = stringResource(R.string.address_required),
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
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
            Spacer(modifier = Modifier.height(16.dp))
            // Phone Number
            Text(
                text = stringResource(R.string.phone_number_required),
                style = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .fillMaxWidth()
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
            Spacer(modifier = Modifier.height(24.dp))
            PrimaryButton(
                onClick = {  },
                content = {
                    Text(text = stringResource(R.string.save))
                }
            )
        }
        IconButton(
            onClick = { /* back to account page */ },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_left),
                contentDescription = stringResource(id = R.string.description_back_button)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditAccountPreview() {
    MdcTheme {
        EditAccountScreen()
    }
}
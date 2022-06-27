package com.firstgroup.secondhand.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.ui.components.PrimaryButton
import com.firstgroup.secondhand.ui.components.SecondaryButton
import com.firstgroup.secondhand.ui.main.home.dummyCategories
import com.google.android.material.composethemeadapter.MdcTheme

class EditDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply{
            setContent {
                MdcTheme {
                    EditDetailScreen()
                }
            }
        }
    }
}

@Composable
fun EditDetailScreen(){
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productCategory by remember { mutableStateOf(Category(0, "Pilih Kategori")) }
    var productDescription by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        val focusManager = LocalFocusManager.current
        // row for header name and back button
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(R.string.header_edit_detail_product),
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            IconButton(
                onClick = { /*TODO back button*/ },
                modifier = Modifier
                    .padding(vertical = 14.dp)
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = null,
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.product_name),
            style = MaterialTheme.typography.body1,
        )
        Spacer(modifier = Modifier.height(4.dp))
        // input product name
        OutlinedTextField(
            value = productName,
            onValueChange = { productName = it },
            textStyle = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    colorResource(id = R.color.neutral_02),
                    RoundedCornerShape(16.dp)
                ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(R.string.product_name),
                    style = MaterialTheme.typography.body1,
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                placeholderColor = colorResource(id = R.color.neutral_02)
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.product_price),
            style = MaterialTheme.typography.body1,
        )
        Spacer(modifier = Modifier.height(4.dp))
        // input product price
        OutlinedTextField(
            value = productPrice,
            onValueChange = { productPrice = it },
            textStyle = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    colorResource(id = R.color.neutral_02),
                    RoundedCornerShape(16.dp)
                ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(R.string.placeholder_product_price),
                    style = MaterialTheme.typography.body1,
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                placeholderColor = colorResource(id = R.color.neutral_02)
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.category),
            style = MaterialTheme.typography.body1,
        )
        Spacer(modifier = Modifier.height(4.dp))
        // select product categories
        OutlinedTextField(
            value = productCategory.name,
            onValueChange = { },
            textStyle = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    colorResource(id = R.color.neutral_02),
                    RoundedCornerShape(16.dp)
                ),
            shape = RoundedCornerShape(16.dp),
            enabled = false,
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(R.string.placeholder_category),
                    style = MaterialTheme.typography.body1,
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                placeholderColor = colorResource(id = R.color.neutral_02),
                disabledTextColor = if(productCategory.id != 0) Color.Black
                else Color.Gray
            ),
            trailingIcon = {
                CategoryDropDown(
                    categories = dummyCategories,
                    onSelectedCategory = {
                        productCategory = it
                    }
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.body1,
        )
        Spacer(modifier = Modifier.height(4.dp))
        // input product description
        OutlinedTextField(
            value = productDescription,
            onValueChange = { productDescription = it },
            textStyle = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(
                    min = 80.dp
                )
                .border(
                    1.dp,
                    colorResource(id = R.color.neutral_02),
                    RoundedCornerShape(16.dp)
                ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            shape = RoundedCornerShape(16.dp),
            singleLine = false,
            placeholder = {
                Text(
                    text = stringResource(R.string.placeholder_description),
                    style = MaterialTheme.typography.body1,
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                placeholderColor = colorResource(id = R.color.neutral_02)
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.product_image),
            style = MaterialTheme.typography.body1,
        )
        Spacer(modifier = Modifier.height(4.dp))
        // select image product
        Image(
            painter = rememberAsyncImagePainter(model = R.drawable.img_product_placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(96.dp)
                .clickable {
                    /*TODO select image*/
                }
        )
    }
    // box for button 'preview' and 'terbitkan'
    Box(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 24.dp
            )
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            SecondaryButton(
                onClick = {
                    /*TODO go to detail as preview*/
                },
                content = {
                    Text(
                        text = stringResource(R.string.preview),
                        style = MaterialTheme.typography.button.copy(
                            color = Color.Black
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(end = 8.dp)
            )
            PrimaryButton(
                onClick = {
                    /*TODO publish product*/
                },
                content = {
                    Text(
                        text = stringResource(R.string.publish),
                        style = MaterialTheme.typography.button
                    )
                },
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun CategoryDropDown(
    categories: List<Category>, 
    onSelectedCategory :(Category) -> Unit)
{
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_bottom_sign),
            contentDescription = null,
            tint = Color.Gray
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        categories.forEach {
            DropdownMenuItem(onClick = {
                onSelectedCategory(it)
                expanded = false
            }) {
                Text(text = it.name)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditDetailPreview(){
    MdcTheme {
        EditDetailScreen()
    }
}
package com.firstgroup.secondhand.ui.main.sell

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.ui.auth.AuthActivity
import com.firstgroup.secondhand.ui.auth.LoginState
import com.firstgroup.secondhand.ui.components.*
import com.firstgroup.secondhand.ui.main.home.CategoriesUiState
import com.firstgroup.secondhand.ui.main.home.dummyCategories
import com.firstgroup.secondhand.ui.main.sell.preview.SellPreview
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class SellFragment : Fragment() {

    private val viewModel: SellViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsState()
                MdcTheme {
                    SellScreen(
                        uiState = uiState,
                        viewModel = viewModel,
                        onLoginClick = ::goToLoginScreen,
                        onImagePickerClick = ::setProductPictures,
                        onPostProductSuccess = ::goToSellListScreen,
                    )
                }
            }
        }
    }

    private fun goToSellListScreen() {
        findNavController().navigate(R.id.action_main_navigation_sell_to_main_navigation_sell_list)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSession()
    }

    private fun goToLoginScreen() {
        startActivity(Intent(requireContext(), AuthActivity::class.java))
    }

    private fun setProductPictures() {
        ImagePicker.with(requireActivity())
            .crop()
            .saveDir(File(activity?.externalCacheDir, "Product Picture"))
            .compress(2048)
            .maxResultSize(1080, 1080)
            .createIntent {
                productPicsResult.launch(it)
            }
    }

    private val productPicsResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val result = it.resultCode
            val uri = it.data
            when (result) {
                Activity.RESULT_OK -> {
                    val image = File(uri?.data?.path ?: "")
                    viewModel.setImage(imageFile = image)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(uri), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    //nothing
                }
            }
        }
}

@Composable
fun SellScreen(
    uiState: SellUiState,
    viewModel: SellViewModel,
    onLoginClick: () -> Unit,
    onImagePickerClick: () -> Unit,
    onPostProductSuccess: () -> Unit,
) {
    LaunchedEffect(key1 = uiState.postProductState) {
        if (uiState.postProductState is PostProductState.Success) {
            onPostProductSuccess.invoke()
        }
    }
    when (uiState.loginState) {
        is LoginState.Idle -> {
            GenericLoadingScreen()
        }
        is LoginState.Loaded -> {
            if (uiState.loginState.isLoggedIn) {
                viewModel.getUser()
                when (uiState.sellState) {
                    SellState.AddNewProduct -> {
                        SellScreen(
                            onProductPictureClick = onImagePickerClick,
                            onPublishClick = viewModel::addProduct,
                            uiState = uiState,
                            onCategorySelected = viewModel::setCategory,
                            onPreviewClick = viewModel::showPreview,
                        )
                    }
                    SellState.PreviewNewProduct -> {
                        SellPreview(
                            onPublishPreviewButtonClicked = viewModel::postProduct,
                            onPreviewBackButtonClicked = viewModel::showAddProduct,
                            onSystemBackPressed = viewModel::showAddProduct,
                            uiState = uiState
                        )
                    }
                }
            } else {
                LoginLayoutPlaceholder(
                    onButtonClick = onLoginClick
                )
            }
        }
    }
}

@Composable
fun SellScreen(
    uiState: SellUiState,
    onProductPictureClick: () -> Unit,
    onCategorySelected: (Category) -> Unit,
    onPublishClick: (String, String, String) -> Unit,
    onPreviewClick: (String, String, String) -> Unit,
) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("0") }
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
            value = uiState.selectedCategoryId.name,
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
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                placeholderColor = colorResource(id = R.color.neutral_02),
                disabledTextColor = if (uiState.selectedCategoryId.id != 0) Color.Black
                else Color.Gray
            ),
            trailingIcon = {
                when (uiState.categoryState) {
                    is CategoriesUiState.Error -> {
                        Box(Modifier.fillMaxWidth()) {
                            Text(text = "Error")
                        }
                    }
                    is CategoriesUiState.Loading -> {
                        CategoryDropDown(
                            categories = dummyCategories,
                            onCategorySelected = onCategorySelected,
                        )
                    }
                    is CategoriesUiState.Success -> {
                        CategoryDropDown(
                            categories = uiState.categoryState.categories,
                            onCategorySelected = onCategorySelected,
                        )
                    }
                }
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
            painter = rememberAsyncImagePainter(
                model = uiState.image                       // Image picked by user
                    ?: R.drawable.img_product_placeholder
            ), // placeholder
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(96.dp)
                .noRippleClickable(
                    onClick = onProductPictureClick
                )
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
        ) {
            SecondaryButton(
                onClick = { onPreviewClick(productName, productDescription, productPrice) },
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
                    onPublishClick(productName, productDescription, productPrice)
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
    onCategorySelected: (Category) -> Unit
) {
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
                onCategorySelected(it)
                expanded = false
            }) {
                Text(text = it.name)
            }
        }
    }
}
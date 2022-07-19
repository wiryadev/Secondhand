package com.firstgroup.secondhand.ui.detail.create_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.ui.components.BottomSheetHandle
import com.firstgroup.secondhand.ui.components.PrimaryButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = arguments?.getInt(PRODUCT_ID_KEY)
        id?.let {
            viewModel.getProductById(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsState()
                MdcTheme {
                    OrderBottomSheetUi(
                        uiState = uiState,
                        onBidButtonClick = viewModel::createOrder,
                        onDismissClick = ::dismiss,
                        toastErrorMessage = ::toastErrorMessage
                    )
                }
            }
        }
    }

    private fun toastErrorMessage(errorMessage: String) {
        val message = if (errorMessage.contains("400")) {
            R.string.error_bad_order
        } else {
            R.string.internal_service_error
        }
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val PRODUCT_ID_KEY =
            "com.firstgroup.secondhand.ui.detail.create_order.OrderBottomSheetFragment"

        fun newInstance(productId: Int) = OrderBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putInt(PRODUCT_ID_KEY, productId)
            }
        }
    }
}

@Composable
fun OrderBottomSheetUi(
    uiState: OrderUiState,
    onBidButtonClick: (Int, Int) -> Unit,
    onDismissClick: () -> Unit,
    toastErrorMessage: (String) -> Unit
) {
    if (uiState.orderState is CreateOrderState.Success) {
        SuccessOrderUi(
            onDismissClick = onDismissClick
        )
    } else {
        OrderFormUi(
            uiState = uiState,
            onBidButtonClick = onBidButtonClick
        )
        LaunchedEffect(key1 = uiState.orderState) {
            if (uiState.orderState is CreateOrderState.Error) {
                toastErrorMessage.invoke(uiState.orderState.message)
            }
        }
    }
}

@Composable
fun SuccessOrderUi(
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        BottomSheetHandle()
        Spacer(modifier = Modifier.height(16.dp))
        // waiting for new lottie raw json files
        val rawLottie by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.order_success2))

        LottieAnimation(
            composition = rawLottie,
            modifier = Modifier.size(180.dp),
        )
        Text(text = stringResource(R.string.order_success))

        Spacer(modifier = Modifier.height(48.dp))
        PrimaryButton(
            onClick = onDismissClick,
            content = {
                Text(text = stringResource(id = R.string.make_another_order))
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun OrderFormUi(
    uiState: OrderUiState,
    onBidButtonClick: (Int, Int) -> Unit,
) {
    var productPrice by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .padding(
                horizontal = 32.dp
            )
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        BottomSheetHandle()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.order_bottomsheet_title),
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.order_bottomsheet_description),
            style = MaterialTheme.typography.body1.copy(
                color = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        // product information
        uiState.product?.let { product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = 4.dp,
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val painterSellerImage = rememberAsyncImagePainter(
                        model = product.imageUrl
                    )
                    // product image
                    Image(
                        painter = painterSellerImage,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp)
                    ) {
                        // product name
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            ),
                        )
                        // product price
                        Text(
                            text = product.price.toString(),
                            style = MaterialTheme.typography.body2.copy(
                                color = Color.Gray
                            ),
                            modifier = Modifier
                                .padding(top = 4.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.bid_price),
                style = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.height(4.dp))
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
                    onDone = { focusManager.clearFocus() },
                    onNext = { focusManager.clearFocus() },
                    onGo = { focusManager.clearFocus() }
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
            Spacer(modifier = Modifier.height(24.dp))
            // button to bid the product
            PrimaryButton(
                onClick = { onBidButtonClick(product.id, productPrice.toInt()) },
                enabled = uiState.orderState !is CreateOrderState.Loading,
                content = {
                    Row {
                        Text(
                            text = stringResource(R.string.bid),
                            style = MaterialTheme.typography.button.copy(
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
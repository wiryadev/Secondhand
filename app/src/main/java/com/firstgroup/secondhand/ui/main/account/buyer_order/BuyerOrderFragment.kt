package com.firstgroup.secondhand.ui.main.account.buyer_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.domain.order.UpdateOrderUseCase
import com.firstgroup.secondhand.ui.components.GenericLoadingScreen
import com.firstgroup.secondhand.ui.components.ListOrders
import com.firstgroup.secondhand.ui.components.OrderDetails
import com.firstgroup.secondhand.ui.components.OrderLayoutPlaceholder
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuyerOrderFragment : Fragment() {

    private val viewModel: BuyerOrderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsState()
                MdcTheme {
                    BuyerOrderScreen(
                        uiState = uiState,
                        onOrderClick = viewModel::getOrderById,
                        onAlertDialogDismiss = viewModel::resetAlertDialogState,
                        updateBidPrice = viewModel::updateBidPrice,
                        onDeleteOrderClick = viewModel::cancelOrderById,
                        showSuccessMessage = ::showSuccessMessage,
                        onBackClick = {
                            findNavController().popBackStack()
                        }
                    )
                }
            }
        }
    }

    private fun showSuccessMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getOrder()
    }
}

@Composable
fun BuyerOrderScreen(
    uiState: BuyerOrderUiState,
    onOrderClick: (Int) -> Unit,
    onAlertDialogDismiss: (Boolean) -> Unit,
    updateBidPrice: (UpdateOrderUseCase.Param) -> Unit,
    onDeleteOrderClick: (Int) -> Unit,
    showSuccessMessage: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    LaunchedEffect(key1 = uiState.message) {
        if (!uiState.message.isNullOrEmpty()) showSuccessMessage.invoke(uiState.message)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(R.string.my_order),
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            IconButton(
                onClick = onBackClick,
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
        Spacer(modifier = Modifier.height(8.dp))
        when (uiState.orders) {
            is BuyerAllOrderState.Error -> {
                // Error Handler
            }
            is BuyerAllOrderState.Success -> {
                if (uiState.orders.ordersData.isEmpty()) {
                    OrderLayoutPlaceholder(message = "Your Order is Empty :(")
                } else {
                    ListOrders(
                        orders = uiState.orders.ordersData,
                        onOrderClick = onOrderClick
                    )
                }
            }
            is BuyerAllOrderState.Loading -> {
                GenericLoadingScreen()
            }
        }
        when (uiState.order) {
            is BuyerOrderState.Idle -> {
                // Do nothing
            }
            is BuyerOrderState.Error -> {
                // Error Handler
            }
            is BuyerOrderState.Success -> {
                OrderDetails(
                    orderData = uiState.order.orderData,
                    resetAlertDialogState = onAlertDialogDismiss,
                    updateBidPrice = updateBidPrice,
                    onDeleteOrderClick = onDeleteOrderClick
                )
            }
        }
    }
}
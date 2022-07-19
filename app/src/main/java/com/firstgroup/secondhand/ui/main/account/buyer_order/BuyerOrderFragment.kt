package com.firstgroup.secondhand.ui.main.account.buyer_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.domain.order.UpdateOrderUseCase
import com.firstgroup.secondhand.ui.components.GenericLoadingScreen
import com.firstgroup.secondhand.ui.components.ListOrders
import com.firstgroup.secondhand.ui.components.OrderDetails
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
                        updateBidPrice = viewModel::updateBidPrice
                    )
                }
            }
        }
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
    updateBidPrice: (UpdateOrderUseCase.Param) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.my_order),
            style = MaterialTheme.typography.h3
        )
        uiState.orders.let {
            when (uiState.orders) {
                is BuyerAllOrderState.Error -> {

                }
                is BuyerAllOrderState.Success -> {
                    ListOrders(
                        orders = uiState.orders.ordersData,
                        onOrderClick = onOrderClick
                    )
                }
                is BuyerAllOrderState.Loading -> {
                    GenericLoadingScreen()
                }
            }
        }
        when(uiState.order) {
            is BuyerOrderState.Idle -> {

            }
            is BuyerOrderState.Error -> {
                // Error Alert Dialog
            }
            is BuyerOrderState.Success -> {
                OrderDetails(
                    orderData = uiState.order.orderData,
                    resetAlertDialogState = onAlertDialogDismiss,
                    updateBidPrice = updateBidPrice
                )
            }
        }
    }
}
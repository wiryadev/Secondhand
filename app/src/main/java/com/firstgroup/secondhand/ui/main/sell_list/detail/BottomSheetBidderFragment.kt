package com.firstgroup.secondhand.ui.main.sell_list.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.ui.components.PrimaryButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetBidderFragment: BottomSheetDialogFragment() {

    private val viewModel: BottomSheetBidderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = arguments?.getInt(ORDER_ID_KEY)
        id?.let {
            viewModel.getOrderAsSeller(it)
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
                MdcTheme{
                    BottomSheetBidderScreen(
                        uiState = uiState,
                        onClickWhatsapp = ::onClickWhatsapp
                    )
                }
            }
        }
    }

    private fun onClickWhatsapp(phoneNumber: String, productName: String) {
        val message = "Hello im the seller of $productName"
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    String.format(
                        "https://api.whatsapp.com/send?phone=%s&text=%s",
                        phoneNumber,
                        message
                    )
                )
            )
        )
    }

    companion object {
        private const val ORDER_ID_KEY = "com.firstgroup.secondhand.ui.main.sell_list.detail.BottomSheetBidderFragment"

        fun newInstance(orderId: Int) = BottomSheetBidderFragment().apply {
            arguments = Bundle().apply {
                putInt(ORDER_ID_KEY, orderId)
            }
        }
    }
}

@Composable
fun BottomSheetBidderScreen(
    uiState: BottomSheetBidderUiState,
    onClickWhatsapp: (String, String) -> Unit
){

    uiState.order?.let { order ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(
                    horizontal = 32.dp
                )
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .size(
                        width = 60.dp,
                        height = 6.dp
                    ),
                backgroundColor = Color.Gray,
                shape = RoundedCornerShape(20.dp)
            ) { }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.bottomsheet_title),
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.bottomsheet_bidder_description),
                style = MaterialTheme.typography.body1.copy(
                    color = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            // buyer and product information
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = 4.dp,
            ) {
                Column(
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.product_match),
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // seller profile image
                        AsyncImage(
                            model = order.buyer.imageUrl,
                            error = painterResource(id = R.drawable.img_profile_placeholder),
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                        )
                        Column(
                            modifier = Modifier
                                .padding(start = 16.dp)
                        ) {
                            // text buyer full name
                            val buyerName = order.buyer.fullName
                            Text(
                                text = buyerName,
                                style = MaterialTheme.typography.body1.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                            // text buyer city
                            val buyerCity = order.buyer.city
                            Text(
                                text = buyerCity, // response is any
                                style = MaterialTheme.typography.body2.copy(
                                    color = Color.Gray
                                ),
                                modifier = Modifier
                                    .padding(top = 4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        // image product
                        val painterProductImage = rememberAsyncImagePainter(
                            model = order.product.imageUrl
                        )
                        Image(
                            painter = painterProductImage,
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        // product detail : name, price, bid
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            val productName = order.product.name
                            Text(
                                text = productName,
                                style = MaterialTheme.typography.body1.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            // product normal price
                            Spacer(modifier = Modifier.height(4.dp))
                            val productPrice = order.product.price
                            Text(
                                text = "Rp $productPrice",
                                style = MaterialTheme.typography.body1.copy(
                                    fontWeight = FontWeight.Bold,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            )
                            // buyer bid for selected product
                            Spacer(modifier = Modifier.height(4.dp))
                            val productBid = order.bidPrice
                            Text(
                                text = "Ditawar Rp $productBid",
                                style = MaterialTheme.typography.body1.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            // button to contact buyer via whatsapp
            PrimaryButton(
                onClick = { onClickWhatsapp(order.buyer.phoneNumber, order.product.name) },
                content = {
                    Row {
                        Text(
                            text = stringResource(R.string.contact_whatsapp),
                            style = MaterialTheme.typography.button.copy(
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        Icon(
                            painter = rememberAsyncImagePainter(model = R.drawable.ic_whatsapp),
                            contentDescription = null,
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
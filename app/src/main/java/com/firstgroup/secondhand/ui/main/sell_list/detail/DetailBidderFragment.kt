package com.firstgroup.secondhand.ui.main.sell_list.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.ui.components.PrimaryButton
import com.firstgroup.secondhand.ui.components.SecondaryButton
import com.firstgroup.secondhand.ui.components.dateFormatter
import com.firstgroup.secondhand.ui.components.orderStatus
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailBidderFragment : Fragment() {

    private val viewModel: DetailBidderViewModel by viewModels()
    private val args: DetailBidderFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsState()
                MdcTheme {
                    val fragmentManager = (activity as FragmentActivity).supportFragmentManager
                    DetailBidderScreen(
                        fragmentManager = fragmentManager,
                        uiState = uiState,
                        onResponseClick = viewModel::respondOrder,
                        onBackClick = { findNavController().popBackStack() },
                        onClickWhatsapp = ::onClickWhatsapp
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getOrderAsSeller(args.id)

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
}

@Composable
fun DetailBidderScreen(
    fragmentManager: FragmentManager,
    uiState: DetailBidderUiState,
    onResponseClick: (Int, Boolean) -> Unit,
    onBackClick: () -> Unit,
    onClickWhatsapp: (String, String) -> Unit

) {
    uiState.order?.let { order ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            // row for header name and back button
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = stringResource(R.string.header_detail_bidder),
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
            Spacer(modifier = Modifier.height(16.dp))
            // card contain buyer detail : image, name and city
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = 4.dp,
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .fillMaxWidth(),
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
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.header_product_bidded),
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            // product detail
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
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(R.string.product_offer),
                            modifier = Modifier.fillMaxWidth(0.5f),
                            style = MaterialTheme.typography.body2.copy(
                                color = Color.Gray
                            )
                        )
                        if (order.transactionDate.isNotEmpty()) {
                            Text(
                                text = stringResource(
                                    id = R.string.buyer_order_detail_date_and_status,
                                    dateFormatter(order.transactionDate),
                                    orderStatus(order.status)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.body2.copy(
                                    color = Color.Gray
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = order.product.name,
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    // product normal price
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Rp ${order.product.price}",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.LineThrough
                        )
                    )
                    // buyer bid for selected product
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Ditawar Rp ${order.bidPrice}",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // for 'tolak' and 'status'
                if (order.status != "declined") {
                    if (order.status == "pending") {
                        SecondaryButton(
                            onClick = { onResponseClick(order.id, false) },
                            content = {
                                Text(
                                    text = stringResource(R.string.decline),
                                    style = MaterialTheme.typography.button.copy(
                                        color = Color.Black
                                    )
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(end = 8.dp)
                        )
                    }
                    // for 'terima' and 'hubungi'
                    PrimaryButton(
                        onClick = {
                            // if status is pending then accept order and show bottom sheet with 'terima' text
                            if (order.status == "pending") {
                                onResponseClick(order.id, true)
                                val bottomSheet = BottomSheetBidderFragment.newInstance(order.id)
                                bottomSheet.show(fragmentManager, bottomSheet.tag)
                            }
                            // if its not then contact buyer via whatsapp with 'hubungi' text
                            else {
                                onClickWhatsapp(order.buyer.phoneNumber, order.product.name)
                            }
                        },
                        content = {
                            Row {
                                Text(
                                    text =
                                    if (order.status == "pending")
                                        stringResource(R.string.accept)
                                    else
                                        stringResource(R.string.contact),
                                    style = MaterialTheme.typography.button,
                                )
                                if (order.status != "pending") {
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Icon(
                                        painter = rememberAsyncImagePainter(model = R.drawable.ic_whatsapp),
                                        contentDescription = null
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxWidth()
                    )
                } else {
                    Text(
                        text = "Penawaranmu ditolak",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.dark_blue_04),
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
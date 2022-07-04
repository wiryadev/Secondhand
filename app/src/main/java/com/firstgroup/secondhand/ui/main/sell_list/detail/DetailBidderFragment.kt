package com.firstgroup.secondhand.ui.main.sell_list.detail

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.network.order.model.GetOrderDto
import com.firstgroup.secondhand.ui.components.PrimaryButton
import com.firstgroup.secondhand.ui.components.SecondaryButton
import com.google.android.material.composethemeadapter.MdcTheme

class DetailBidderFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    val fragmentManager = (activity as FragmentActivity).supportFragmentManager
                    DetailBidderScreen(fragmentManager = fragmentManager)
                }
            }
        }
    }
}

@Composable
fun DetailBidderScreen(
    fragmentManager : FragmentManager
){
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
                val painterBuyerImage = rememberAsyncImagePainter(
                    model = dummyOrder.product.imageUrl // it should user.imageUrl, but order response doesn't give any image from buyer
                )
                // seller profile image
                Image(
                    painter = painterBuyerImage,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                ) {
                    // text buyer full name
                    Text(
                        text = dummyOrder.user.fullName,
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    // text buyer city
                    Text(
                        text = dummyOrder.user.city.toString(), // response is any
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
                model = dummyOrder.product.imageUrl
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
                    Text(
                        text = "16 Apr 14:04", // it should date, but no date in order response
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.body2.copy(
                            color = Color.Gray
                        )
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = dummyOrder.product.name,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                // product normal price
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rp ${dummyOrder.product.basePrice}",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.LineThrough
                    )
                )
                // buyer bid for selected product
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ditawar Rp ${dummyOrder.price}",
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
            SecondaryButton(
                onClick = {
                    // if its pending then decline order with 'tolak' text
                    if (dummyOrder.status == "pending") {
                        /*TODO decline response*/
                    }
                    // if its not then show bottom status with 'status' text
                    else {
                        val bsStatus = BottomSheetStatusFragment()
                        bsStatus.show(fragmentManager, bsStatus.tag)
                    }
                },
                content = {
                    Text(
                        text =
                            if (dummyOrder.status == "pending")
                                stringResource(R.string.decline)
                            else
                                stringResource(R.string.status),
                        style = MaterialTheme.typography.button.copy(
                            color = Color.Black
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(end = 8.dp)
            )
            // for 'terima' and 'hubungi'
            PrimaryButton(
                onClick = {
                    // if status is pending then accept order and show bottom sheet with 'terima' text
                    if (dummyOrder.status == "pending") {
                        /*TODO  Accept order*/
                        val bottomSheet = BottomSheetBidderFragment()
                        bottomSheet.show(fragmentManager, bottomSheet.tag)
                    }
                    // if its not then contact buyer via whatsapp with 'hubungi' text
                    else {
                        /*TODO Contact via Whatsapp*/
                    }
                },
                content = {
                    Row {
                        Text(
                            text =
                            if (dummyOrder.status == "pending")
                                stringResource(R.string.accept)
                            else
                                stringResource(R.string.contact),
                            style = MaterialTheme.typography.button,
                            modifier =
                            if (dummyOrder.status == "pending"){
                                Modifier.fillMaxWidth()
                            }
                            else{
                                Modifier.fillMaxWidth(0.8f)
                            }
                        )
                        if (dummyOrder.status != "pending") {
                            Icon(
                                painter = rememberAsyncImagePainter(model = R.drawable.ic_whatsapp),
                                contentDescription = null
                            )
                        }
                    }
                },
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDetailBidder(){
    MdcTheme {
//        DetailBidderScreen()
    }
}

val dummyOrder = GetOrderDto(
    id = 159,
    productId = 1,
    buyerId = 274,
    price = 100000,
    status = "pending",
    product = GetOrderDto.Product(
        name = "adidas red",
        description = "null",
        basePrice = 100000,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/market-final-project.appspot.com/o/avatar%2FAV-1655983009420-hina.PNG?alt=media",
        imageName = "adidas image",
        location = "bandung",
        userId = 5,
        status = "pending"
    ),
    user = GetOrderDto.User(
        fullName = "Agus WIlliam",
        email = "agusrt026@mail.com",
        phoneNumber = "081208120812",
        address = "bekasi",
        city = "Kab. Bekasi"
    )
)
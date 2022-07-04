package com.firstgroup.secondhand.ui.main.sell_list.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.ui.components.PrimaryButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.composethemeadapter.MdcTheme

class BottomSheetBidderFragment: BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme{
                    // bottom sheet
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
                        ){ }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = context.getString(R.string.bottomsheet_title),
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = context.getString(R.string.bottomsheet_bidder_description),
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
                                    text = context.getString(R.string.product_match),
                                    style = MaterialTheme.typography.body1.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
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
                                Spacer(modifier = Modifier.height(16.dp))
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
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        // button to contact buyer via whatsapp
                        PrimaryButton(
                            onClick = { /*TODO whatsapp*/ },
                            content = {
                                Row {
                                    Text(
                                        text = context.getString(R.string.contact_whatsapp),
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
                            }
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}
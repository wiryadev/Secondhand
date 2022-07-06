package com.firstgroup.secondhand.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.ui.components.PrimaryButton
import com.firstgroup.secondhand.ui.detail.create_order.OrderBottomSheetFragment
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsState()
                MdcTheme {
                    DetailScreen(
                        uiState = uiState,
                        fragmentManager = parentFragmentManager
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.getProductDetailById(args.id)
//            viewModel.checkUser()   // commented until login checking implemented
        }
    }
}

@Composable
fun DetailScreen(
    uiState: DetailUiState,
    fragmentManager: FragmentManager
) {
    uiState.product?.let { product ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val painter = rememberAsyncImagePainter(
                model = product.imageUrl
            )
            // background image that use product image
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(height = 300.dp)
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                // spacer from top of parent
                Spacer(modifier = Modifier.height(265.dp))
                // first card, contain product name, category, price
                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 4.dp,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                    ) {
                        // text product name
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .padding(
                                    top = 16.dp,
                                )
                        )
                        // text product category
                        Text(
                            text = product.category,
                            style = MaterialTheme.typography.body2.copy(
                                color = Color.Gray
                            ),
                            modifier = Modifier
                                .padding(
                                    top = 4.dp,
                                )
                        )
                        // text product price
                        Text(
                            text = "Rp ${product.price}",
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .padding(
                                    top = 8.dp,
                                    bottom = 16.dp
                                )
                        )
                    }
                }
                // second card, contain seller image, name and city
                Card(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
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
                            model = product.seller?.imageUrl
                        )
                        // seller profile image
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
                            // text seller full name
                            Text(
                                text = product.seller?.name ?: "No Name",
                                style = MaterialTheme.typography.body1.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                            // text seller address
                            Text(
                                text = product.seller?.city ?: "No Location",
                                style = MaterialTheme.typography.body2.copy(
                                    color = Color.Gray
                                ),
                                modifier = Modifier
                                    .padding(top = 4.dp)
                            )
                        }
                    }
                }
                // third card, contain product description
                // check if its description available
                val textDescription =
                    product.description ?: stringResource(R.string.no_description)

                Card(
                    modifier = Modifier
                        .padding(
                            top = 19.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 4.dp,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(all = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.description),
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = 16.dp),
                            text = textDescription,
                            style = MaterialTheme.typography.body1.copy(
                                color = Color.Gray
                            )
                        )
                    }
                }
                // add spacer so all description text is readable
                Spacer(modifier = Modifier.height(88.dp))
            }

            // floating back button
            Card(
                modifier = Modifier
                    .padding(
                        top = 44.dp,
                        start = 16.dp
                    )
                    .clickable { },
                shape = RoundedCornerShape(20.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    modifier = Modifier.size(24.dp),
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
        // box for button 'terbitkan'
        Box(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 24.dp
                )
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            PrimaryButton(
                onClick = {
                    val orderBottomsheet = OrderBottomSheetFragment.newInstance(uiState.product.id)
                    orderBottomsheet.show(fragmentManager, orderBottomsheet.tag)
                },
                content = {
                    Text(
                        text = stringResource(R.string.bid),
                        style = MaterialTheme.typography.button
                    )
                }
            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun DetailPreview() {
//    MdcTheme {
////        DetailScreen()
//    }
//}

//val dummyUser = User(
//    fullName = "Agus William",
//    email = "agus12@mail.com",
//    password = "123456",
//    phoneNo = "081907280637",
//    address = "Bekasi",
//    profilePicture = "https://firebasestorage.googleapis.com/v0/b/market-final-project.appspot.com/o/avatar%2FAV-1655983009420-hina.PNG?alt=media",
//    city = "Bekasi"
//)


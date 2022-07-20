package com.firstgroup.secondhand.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.ui.auth.AuthActivity
import com.firstgroup.secondhand.ui.auth.LoginState
import com.firstgroup.secondhand.ui.components.GenericLoadingScreen
import com.firstgroup.secondhand.ui.components.PrimaryButton
import com.firstgroup.secondhand.ui.components.SecondaryButton
import com.firstgroup.secondhand.ui.detail.create_order.OrderBottomSheetFragment
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsState()
                MdcTheme {
                    DetailScreen(uiState = uiState,
                        fragmentManager = parentFragmentManager,
                        onLoginClick = ::goToLoginScreen,
                        onBackClick = { findNavController().popBackStack() },
                        onCheckWishlist = {
                            viewModel.getWishlist(args.id)
                        },
                        onWishlistClick = {
                            viewModel.addToWishlist(it)
                            viewModel.getWishlist(it)
                        }
                    )
                }
            }
        }
    }

    private fun goToLoginScreen() {
        startActivity(Intent(requireContext(), AuthActivity::class.java))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSession()
        
        if (savedInstanceState == null) {
            viewModel.getProductDetailById(args.id)
        }
    }
}

@Composable
fun DetailScreen(
    uiState: DetailUiState,
    fragmentManager: FragmentManager,
    onLoginClick: () -> Unit,
    onCheckWishlist: () -> Unit,
    onWishlistClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    LaunchedEffect(key1 = uiState.loginState) {
        if (uiState.loginState is LoginState.Loaded && uiState.loginState.isLoggedIn) {
            onCheckWishlist.invoke()
        }
    }

    uiState.product?.let { product ->
        Box(
            modifier = Modifier.fillMaxSize()
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
                modifier = Modifier.verticalScroll(rememberScrollState())
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
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {
                        // text product name
                        Text(
                            text = product.name, style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            ), modifier = Modifier.padding(
                                top = 16.dp,
                            )
                        )
                        // text product category
                        Text(
                            text = product.category, style = MaterialTheme.typography.body2.copy(
                                color = Color.Gray
                            ), modifier = Modifier.padding(
                                top = 4.dp,
                            )
                        )
                        // text product price
                        Text(
                            text = "Rp ${product.price}",
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(
                                top = 8.dp, bottom = 16.dp
                            )
                        )
                    }
                }
                // second card, contain seller image, name and city
                Card(
                    modifier = Modifier
                        .padding(
                            top = 16.dp, start = 16.dp, end = 16.dp
                        )
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 4.dp,
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                    ) {
                        val painterSellerImage = rememberAsyncImagePainter(
                            model = product.seller?.imageUrl
                        )
                        // seller profile image
                        Image(
                            painter = painterSellerImage,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                        Column(
                            modifier = Modifier.padding(start = 16.dp)
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
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
                // third card, contain product description
                // check if its description available
                val textDescription = product.description

                Card(
                    modifier = Modifier
                        .padding(
                            top = 19.dp, start = 16.dp, end = 16.dp
                        )
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 4.dp,
                ) {
                    Column(
                        modifier = Modifier.padding(all = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.description),
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            modifier = Modifier.padding(top = 16.dp),
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
                        top = 44.dp, start = 16.dp
                    )
                    .clickable { onBackClick() },
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
        Row(
            modifier = Modifier
                .padding(
                    start = 16.dp, end = 16.dp, bottom = 24.dp
                )
                .fillMaxSize(), verticalAlignment = Alignment.Bottom
        ) {
            when (uiState.loginState) {
                is LoginState.Loaded -> {
                    if (uiState.loginState.isLoggedIn) {
                        PrimaryButton(
                            modifier = Modifier
                                .fillMaxWidth(0.8f),
                            onClick = {
                                val orderBottomsheet =
                                    OrderBottomSheetFragment.newInstance(uiState.product.id)
                                orderBottomsheet.show(fragmentManager, orderBottomsheet.tag)
                            },
                            content = {
                                Text(
                                    text = stringResource(R.string.bid),
                                    style = MaterialTheme.typography.button
                                )
                            },
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        SecondaryButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                if (!uiState.isWishListed) {
                                    onWishlistClick(uiState.product.id)
                                }
                            },
                            content = {
                                Icon(
                                    imageVector = if (uiState.isWishListed) ImageVector.vectorResource(
                                        id = R.drawable.ic_wishlist
                                    )
                                    else ImageVector.vectorResource(id = R.drawable.ic_wishlist_border),
                                    contentDescription = null
                                )
                            },
                            enabled = !uiState.isWishListed,
                        )
                    } else {
                        PrimaryButton(
                            onClick = onLoginClick,
                            content = {
                                Text(
                                    text = stringResource(R.string.login),
                                    style = MaterialTheme.typography.button
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
                is LoginState.Idle -> {
                    GenericLoadingScreen()
                }
            }

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


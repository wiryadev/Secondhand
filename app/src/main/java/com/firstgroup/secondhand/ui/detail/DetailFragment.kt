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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.core.model.User
import com.firstgroup.secondhand.ui.components.PrimaryButton
import com.firstgroup.secondhand.ui.main.home.dummyProduct
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply{
            setContent {
                MdcTheme {

                }
            }
        }
    }
}

@Composable
fun DetailScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val painter = rememberAsyncImagePainter(
            model = dummyProduct.imageUrl
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
        Column (modifier = Modifier
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
                        text = dummyProduct.name,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                            )
                    )
                    // text product category
                    Text(
                        text = dummyProduct.category,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .padding(
                                top = 4.dp,
                            )
                    )
                    // text product price
                    Text(
                        text = "Rp ${dummyProduct.price}",
                        style = MaterialTheme.typography.body1,
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
                Row(modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val painterSellerImage = rememberAsyncImagePainter(
                        model = dummyUser.profilePicture
                    )
                    // seller profile image
                    Image(painter = painterSellerImage,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                    )
                    Column(modifier = Modifier
                        .padding(start = 16.dp)
                    ){
                        // text seller full name
                        Text(text = dummyUser.fullName,
                            style = MaterialTheme.typography.body1,
                        )
                        // text seller address
                        Text(text = dummyUser.address,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier
                                .padding(top = 4.dp)
                        )
                    }
                }
            }
            // third card, contain product description
            // check if its description available
            if (dummyProduct.description != null) {
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
                            text = "Deskripsi",
                            style = MaterialTheme.typography.body1
                        )
                        Text(text = dummyProduct.description,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
                // add spacer so all description text is readable
                Spacer(modifier = Modifier.height(88.dp))
            }
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
    Box(modifier = Modifier
        .padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 24.dp
        )
        .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
        PrimaryButton(onClick = {

            },
            content = {
                Text(text = "Terbitkan",
                    style = MaterialTheme.typography.button
                )
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailPreview(){
    MdcTheme {
        DetailScreen()
    }
}

val dummyUser = User(
    fullName = "Agus William",
    email = "agus12@mail.com",
    password = "123456",
    phoneNo = "081907280637",
    address = "Bekasi",
    profilePicture = "https://firebasestorage.googleapis.com/v0/b/market-final-project.appspot.com/o/avatar%2FAV-1655983009420-hina.PNG?alt=media"
)


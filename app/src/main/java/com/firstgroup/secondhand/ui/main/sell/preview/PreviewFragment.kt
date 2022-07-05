package com.firstgroup.secondhand.ui.main.sell.preview

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.ui.components.PrimaryButton
import com.firstgroup.secondhand.ui.main.home.CategoriesUiState
import com.firstgroup.secondhand.ui.main.home.dummyCategories
import com.firstgroup.secondhand.ui.main.sell.CategoryDropDown
import com.firstgroup.secondhand.ui.main.sell.SellUiState

@Composable
fun SellPreview(
    onPublishPreviewButtonClicked: () -> Unit,
    onPreviewBackButtonClicked: () -> Unit,
    onSystemBackPressed: () -> Unit,
    uiState: SellUiState
) {
    BackHandler(enabled = true) {
        onSystemBackPressed()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // background image that use product image
        Image(
            painter = rememberAsyncImagePainter(
                model = uiState.image ?: R.drawable.img_profile_placeholder
            ),
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
                        text = uiState.productData?.name ?: "Product Name",
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
                        text = uiState.selectedCategoryId.name ,
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
                        text = uiState.productData?.basePrice.toString() ,
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
                    // seller profile image
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = uiState.recentUser?.profilePicture ?: R.drawable.img_profile_placeholder
                        ),
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
                            text = uiState.recentUser?.fullName ?: "Seller Name",
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold
                            ),
                        )
                        // text seller address
                        Text(
                            text = uiState.recentUser?.city ?: "Seller Location",
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
                        text = uiState.productData?.description ?: "Product Description",
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
                .clickable {
                    onPreviewBackButtonClicked()
                },
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
                onPublishPreviewButtonClicked()
            },
            content = {
                Text(
                    text = stringResource(R.string.publish),
                    style = MaterialTheme.typography.button
                )
            }
        )
    }
}
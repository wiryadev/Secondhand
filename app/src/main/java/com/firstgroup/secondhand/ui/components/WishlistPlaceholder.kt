package com.firstgroup.secondhand.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.firstgroup.secondhand.R

@Composable
fun WishlistLayoutPlaceholder(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val rawLottie by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.wishlist_placeholder))
        Spacer(modifier = Modifier.weight(1f))
        LottieAnimation(
            composition = rawLottie,
            modifier = Modifier.size(180.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Go find the thing you want!",
            style = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
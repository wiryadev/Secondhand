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
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.firstgroup.secondhand.R

@Composable
fun SearchLayoutPlaceholder(
    message: String,
    isIdle: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val lottie = if (isIdle) {
            R.raw.search_idle
        } else {
            R.raw.search_not_found
        }

        val rawLottie by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(lottie))
        Spacer(modifier = Modifier.weight(1f))
        LottieAnimation(
            composition = rawLottie,
            modifier = Modifier.size(254.dp),
            iterations = LottieConstants.IterateForever
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
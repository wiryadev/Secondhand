package com.firstgroup.secondhand.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.firstgroup.secondhand.R

@Composable
fun LoginLayoutPlaceholder(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val rawLottie by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.login_and_sign_up2))
        
        Spacer(modifier = Modifier.weight(1f))
        LottieAnimation(
            composition = rawLottie,
            modifier = Modifier.size(180.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = stringResource(R.string.login_placeholder_commentary_text))

        Spacer(modifier = Modifier.height(48.dp))
        PrimaryButton(
            onClick = {
                onButtonClick()
            },
            content = {
                Text(text = stringResource(id = R.string.login))
            }
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
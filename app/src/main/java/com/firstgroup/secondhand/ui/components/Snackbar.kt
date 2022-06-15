package com.firstgroup.secondhand.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.material.composethemeadapter.MdcTheme
import kotlinx.coroutines.delay

@Composable
fun TopSnackBar(
    message: String,
    isError: Boolean,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier,
    showSnackbar: Boolean = message.isNotBlank(),
) {

    LaunchedEffect(key1 = showSnackbar) {
        delay(3000)
        if (showSnackbar) {
            onDismissClick()
        }
    }

    AnimatedVisibility(
        visible = showSnackbar,
        enter = fadeIn() + slideInVertically(
            animationSpec = tween(500),
            initialOffsetY = { -it }
        ),
        exit = slideOutVertically(
            animationSpec = tween(500),
            targetOffsetY = { -it }
        ) + fadeOut(),
        modifier = modifier,
    ) {
        Card(
            backgroundColor = if (isError) Color.Red else Color.Green,
            elevation = 8.dp,
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(start = 24.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(end = 16.dp),
                )
                IconButton(
                    onClick = onDismissClick
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Dismiss",
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TopAppSnackbarPreview() {
    MdcTheme {
        TopSnackBar(
            message = "Something in the way",
            isError = false,
            onDismissClick = { /**/ }
        )
    }
}
package com.firstgroup.secondhand.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.firstgroup.secondhand.R

@Composable
fun GenericErrorScreen(
    message: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(16.dp),
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4.copy(
                color = MaterialTheme.colors.error
            ),
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}
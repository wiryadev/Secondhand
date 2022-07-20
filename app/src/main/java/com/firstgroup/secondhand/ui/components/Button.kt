package com.firstgroup.secondhand.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.firstgroup.secondhand.R

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .heightIn(min = 48.dp),
        shape = RoundedCornerShape(16.dp),
        enabled = enabled,
//        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp)
    ) {
        content()
    }
}

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
    borderColor: Color = MaterialTheme.colors.primary,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.heightIn(min = 48.dp),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor,
        ),
        shape = RoundedCornerShape(16.dp),
        enabled = enabled,
    ) {
        content()
    }
}
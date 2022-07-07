package com.firstgroup.secondhand.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.BottomSheetHandle(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(
                width = 64.dp,
                height = 8.dp
            )
            .align(alignment = Alignment.CenterHorizontally)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colors.onSecondary)
    )
}
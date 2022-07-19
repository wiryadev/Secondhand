package com.firstgroup.secondhand.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun dateFormatter(date: String): String {
    val transactionDateFormat = DateTimeFormatter.ofPattern("dd MMM, HH:mm")

    return OffsetDateTime.parse(date).format(transactionDateFormat)
}

fun orderStatus(status: String): String {
    return when (status) {
        "pending" -> "Order Pending"
        "declined" -> "Order Declined"
        "accepted" -> "Order Accepted"
        else -> "Status Unknown"
    }
}
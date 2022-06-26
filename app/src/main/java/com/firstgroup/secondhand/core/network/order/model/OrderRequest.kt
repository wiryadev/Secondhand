package com.firstgroup.secondhand.core.network.order.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderRequest(
    @Json(name = "product_id")
    val productId: Int,
    @Json(name = "bid_price")
    val bidPrice: Int,
)

package com.firstgroup.secondhand.utils

import com.firstgroup.secondhand.core.model.CreateOrder
import com.firstgroup.secondhand.domain.order.CreateOrderUseCase

object OrderDummyDataProvider {

    fun provideValidCreateOrderParam() = CreateOrderUseCase.Param(
        productId = 5,
        bidPrice = 25000,
    )

    fun provideValidCreateOrderResponse() = CreateOrder(
        id = provideValidCreateOrderParam().productId,
        price = 30000,
        status = "pending",
        date = "22-10-2022"
    )

}
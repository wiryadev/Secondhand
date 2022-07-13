package com.firstgroup.secondhand.utils

import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.core.network.product.model.ProductRequest
import com.firstgroup.secondhand.domain.product.AddNewProductUseCase
import org.mockito.kotlin.mock
import java.io.File

object ProductDummyDataProvider {

    private val file: File = mock()

    fun provideValidProductParam() = AddNewProductUseCase.Param(
        name = "name",
        description = "description",
        basePrice = 1000,
        categoryIds = listOf(1),
        location = "city",
        image = file
    )

    fun provideValidProductRequest() = ProductRequest(
        name = "name",
        description = "description",
        basePrice = 1000,
        categoryIds = listOf(1),
        location = "city",
        image = file
    )

    fun provideValidProduct() = Product(
        id = 1,
        name = "name",
        description = "description",
        price = 1000,
        imageUrl = "imageUrl",
        location = "city",
        userId = 1,
        category = "category",
        seller = null
    )

}
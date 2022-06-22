package com.firstgroup.secondhand.core.network.product.model

import com.firstgroup.secondhand.core.network.utils.RequestUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

data class ProductRequest(
    val name: String,
    val description: String,
    val basePrice: Int,
    val categoryIds: List<Int>,
    val location: String,
    val image: File,
) {
    data class FormData(
        val requestBody: HashMap<String, RequestBody>,
        val multipart: MultipartBody.Part,
        val categoryIds: List<Int>
    )

    fun toFormData(): FormData {
        val map = hashMapOf(
            "name" to RequestUtil.createPartFromString(name),
            "description" to RequestUtil.createPartFromString(description),
            "base_price" to RequestUtil.createPartFromString(basePrice.toString()),
            "location" to RequestUtil.createPartFromString(location),
        )

        val body = RequestUtil.createPartFromFile(image)

        return FormData(map, body, categoryIds)
    }
}
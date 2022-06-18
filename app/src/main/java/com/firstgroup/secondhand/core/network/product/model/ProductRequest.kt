package com.firstgroup.secondhand.core.network.product.model

import com.firstgroup.secondhand.core.network.utils.RequestUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

data class ProductRequest(
    val name: String,
    val description: String,
    val base_price: String,
    val category_ids: String,
    val location: String,
    val image: File,
) {
    data class FormData(
        val requestBody: HashMap<String, RequestBody>,
        val multipart: MultipartBody.Part,
    )

    fun toFormData(): FormData {
        val map = hashMapOf(
            "name" to RequestUtil.createPartFromString(name),
            "description" to RequestUtil.createPartFromString(description),
            "base_price" to RequestUtil.createPartFromString(base_price),
            "category_ids" to RequestUtil.createPartFromString(category_ids),
            "location" to RequestUtil.createPartFromString(location),
        )

        val body = RequestUtil.createPartFromFile(image)

        return FormData(map, body)
    }
}
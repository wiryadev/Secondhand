package com.firstgroup.secondhand.core.network.auth.model

import com.firstgroup.secondhand.core.network.utils.RequestUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

data class RegisterUserRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val phoneNo: String,
    val address: String,
    val city: String,
    val image: File? = null
) {
    data class FormData(
        val requestBody: HashMap<String, RequestBody>,
        val multipart: MultipartBody.Part?,
    )

    fun toFormData(): FormData {
        val map = hashMapOf(
            "full_name" to RequestUtil.createPartFromString(fullName),
            "email" to RequestUtil.createPartFromString(email),
            "password" to RequestUtil.createPartFromString(password),
            "phone_number" to RequestUtil.createPartFromString(phoneNo),
            "address" to RequestUtil.createPartFromString(address),
            "city" to RequestUtil.createPartFromString(city),
        )

        val body = image?.let {
            RequestUtil.createPartFromFile(it)
        }

        return FormData(map, body)
    }
}

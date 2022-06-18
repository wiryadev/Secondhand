package com.firstgroup.secondhand.core.network.utils

import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object RequestUtil {

    val moshi: Moshi by lazy {
        Moshi.Builder().build()
    }

    inline fun <reified T> createJsonRequestBody(data: T): RequestBody {
        val adapter = moshi.adapter(T::class.java)
        return adapter.toJson(data)
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    fun createPartFromString(stringData: String): RequestBody {
        return stringData.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    fun createPartFromFile(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/jpg".toMediaType())
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }

}
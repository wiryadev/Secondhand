package com.firstgroup.secondhand.core.network.utils

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File

object RequestUtil {

    fun createJsonRequestBody(vararg params: Pair<String, String>) =
        JSONObject(mapOf(*params)).toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

    fun createPartFromString(stringData: String): RequestBody {
        return stringData.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    fun createPartFromFile(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/jpg".toMediaType())
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }

}
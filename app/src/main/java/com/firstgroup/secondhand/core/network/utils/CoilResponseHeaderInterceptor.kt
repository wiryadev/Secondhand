package com.firstgroup.secondhand.core.network.utils

import okhttp3.Interceptor
import okhttp3.Response

class CoilResponseHeaderInterceptor(
    private val name: String,
    private val value: String,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return response.newBuilder().header(name, value).build()
    }

}
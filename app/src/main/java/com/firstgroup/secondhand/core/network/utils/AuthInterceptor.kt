package com.firstgroup.secondhand.core.network.utils

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {

    private var token: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder: Request.Builder = request.newBuilder()

        val needCredential = request.header(NO_AUTH_HEADER_KEY) == null
        if (needCredential) {
            token?.let {
                requestBuilder.addHeader("access_token", it)
            } ?: throw RuntimeException("Token should not be null")
        }

        return chain.proceed(requestBuilder.build())
    }

    fun setToken(newToken: String) {
        if (newToken.isNotEmpty()) {
            token = newToken
        }
    }

    fun deleteToken() {
        token = null
    }

    companion object {
        const val NO_AUTH_HEADER_KEY = "No-Authentication"
    }
}
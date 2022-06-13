package com.firstgroup.secondhand.core.network.auth.retrofit

import com.firstgroup.secondhand.core.network.auth.model.LoginResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    suspend fun login(
        @Body loginData: RequestBody
    ): LoginResponse

}
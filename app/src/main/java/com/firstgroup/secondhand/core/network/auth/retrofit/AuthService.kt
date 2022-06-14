package com.firstgroup.secondhand.core.network.auth.retrofit

import com.firstgroup.secondhand.core.network.auth.model.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface AuthService {

    @POST("auth/login")
    suspend fun login(
        @Body loginData: RequestBody
    ): LoginResponse

    @Multipart
    @POST("auth/register")
    suspend fun register(
        @PartMap partMap: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part? = null,
    ): LoginResponse

}
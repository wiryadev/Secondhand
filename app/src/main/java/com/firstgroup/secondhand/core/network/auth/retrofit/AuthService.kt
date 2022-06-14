package com.firstgroup.secondhand.core.network.auth.retrofit

import com.firstgroup.secondhand.core.network.auth.model.LoginResponse
import com.firstgroup.secondhand.core.network.auth.model.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

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
    ): UserResponse

}
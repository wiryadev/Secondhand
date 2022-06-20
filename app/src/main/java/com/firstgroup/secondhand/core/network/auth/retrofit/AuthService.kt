package com.firstgroup.secondhand.core.network.auth.retrofit

import com.firstgroup.secondhand.core.network.auth.model.LoginDto
import com.firstgroup.secondhand.core.network.auth.model.UserDto
import com.firstgroup.secondhand.core.network.utils.AuthInterceptor.Companion.NO_AUTH_HEADER_KEY
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface AuthService {

    @POST("auth/login")
    @Headers("$NO_AUTH_HEADER_KEY: true")
    suspend fun login(
        @Body loginData: RequestBody
    ): LoginDto

    @Multipart
    @POST("auth/register")
    @Headers("$NO_AUTH_HEADER_KEY: true")
    suspend fun register(
        @PartMap partMap: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part? = null,
    ): UserDto

    @GET("auth/user")
    suspend fun getUser(): UserDto

    @PUT("auth/user")
    suspend fun updateUser(
        @PartMap partMap: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part? = null,
    ): UserDto

}
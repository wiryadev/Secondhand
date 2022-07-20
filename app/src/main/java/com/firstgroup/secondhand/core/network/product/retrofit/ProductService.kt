package com.firstgroup.secondhand.core.network.product.retrofit

import com.firstgroup.secondhand.core.network.product.model.*
import com.firstgroup.secondhand.core.network.utils.AuthInterceptor.Companion.NO_AUTH_HEADER_KEY
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ProductService {

    /**
     * Buyer
     */
    @GET("buyer/product?status=available")
    @Headers("${NO_AUTH_HEADER_KEY}: true")
    suspend fun getProductsAsBuyer(
        @Query("page") page: Int,
        @Query("per_page") size: Int,
        @Query("category_id") categoryId: Int? = null,
        @Query("search") search: String? = null,
    ): List<ProductDto>

    @GET("buyer/product/{id}")
    @Headers("${NO_AUTH_HEADER_KEY}: true")
    suspend fun getProductByIdAsBuyer(
        @Path("id") productId: Int,
    ): ProductDetailDto


    /**
     * Seller
     */
    @GET("seller/product")
    suspend fun getProductsAsSeller(): List<ProductDto>

    @GET("seller/product/{id}")
    suspend fun getProductByIdAsSeller(
        @Path("id") productId: Int,
    ): ProductDto

    @Multipart
    @POST("seller/product")
    suspend fun addNewProduct(
        @PartMap partMap: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part,
        @Part("category_ids") categoryIds: List<Int>,
    ): AddProductDto

    @GET("seller/category")
    @Headers("${NO_AUTH_HEADER_KEY}: true")
    suspend fun getCategories(): List<CategoryDto>

    @GET("seller/banner")
    suspend fun getBanners(): List<BannerDto>

}
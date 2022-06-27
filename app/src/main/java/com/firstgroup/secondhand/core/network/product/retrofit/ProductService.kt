package com.firstgroup.secondhand.core.network.product.retrofit

import com.firstgroup.secondhand.core.network.product.model.AddProductDto
import com.firstgroup.secondhand.core.network.product.model.BannerDto
import com.firstgroup.secondhand.core.network.product.model.CategoryDto
import com.firstgroup.secondhand.core.network.product.model.ProductDto
import com.firstgroup.secondhand.core.network.utils.AuthInterceptor.Companion.NO_AUTH_HEADER_KEY
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ProductService {

    /**
     * Buyer
     */
    @GET("buyer/product")
    @Headers("${NO_AUTH_HEADER_KEY}: true")
    suspend fun getProductsAsBuyer(): List<ProductDto>

    @GET("buyer/product/{id}")
    suspend fun getProductByIdAsBuyer(
        @Path("id") productId: Int,
    ): ProductDto


    /**
     * Seller
     */
    @GET("seller/product")
    suspend fun getProductsAsSeller(): List<ProductDto>

    @GET("seller/product/{id}")
    suspend fun getProductByIdAsSeller(
        @Path("id") productId: Int,
    ): ProductDto

    @POST("seller/product")
    suspend fun addNewProduct(
        @PartMap partMap: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part,
        @Part("category_ids[]") categoryIds: List<Int>,
    ): AddProductDto

    @GET("seller/category")
    @Headers("${NO_AUTH_HEADER_KEY}: true")
    suspend fun getCategories(): List<CategoryDto>

    @GET("seller/banner")
    suspend fun getBanners(): List<BannerDto>

}
package com.firstgroup.secondhand.core.network.wishlist.retrofit

import com.firstgroup.secondhand.core.network.wishlist.model.AddWishlistDto
import com.firstgroup.secondhand.core.network.wishlist.model.DeleteWishlistDto
import com.firstgroup.secondhand.core.network.wishlist.model.WishlistDto
import retrofit2.http.*

interface WishlistService {

    @POST("buyer/wishlist")
    @FormUrlEncoded
    suspend fun addToWishlist(
        @Field("product_id") productId: Int,
    ): AddWishlistDto

    @GET("buyer/wishlist")
    suspend fun getWishlist(): List<WishlistDto>

    @GET("buyer/wishlist/{id}")
    suspend fun getWishlistById(
        @Path("id") id: Int
    ): WishlistDto

    @DELETE("buyer/wishlist/{id}")
    suspend fun removeFromWishlist(
        @Path("id") id: Int
    ): DeleteWishlistDto

}

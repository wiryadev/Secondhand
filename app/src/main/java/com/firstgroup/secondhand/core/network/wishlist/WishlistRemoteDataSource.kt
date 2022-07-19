package com.firstgroup.secondhand.core.network.wishlist

import com.firstgroup.secondhand.core.network.wishlist.model.AddWishlistDto
import com.firstgroup.secondhand.core.network.wishlist.model.DeleteWishlistDto
import com.firstgroup.secondhand.core.network.wishlist.model.WishlistDto

interface WishlistRemoteDataSource {

    suspend fun addToWishlist(productId: Int): AddWishlistDto

    suspend fun getWishlist(): List<WishlistDto>

    suspend fun getWishlistById(id: Int): WishlistDto

    suspend fun removeFromWishlist(id: Int): DeleteWishlistDto

}
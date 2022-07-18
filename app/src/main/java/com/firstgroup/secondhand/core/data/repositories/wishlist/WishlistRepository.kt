package com.firstgroup.secondhand.core.data.repositories.wishlist

import com.firstgroup.secondhand.core.model.BasicResponse
import com.firstgroup.secondhand.core.model.Wishlist

interface WishlistRepository {

    suspend fun addToWishlist(productId: Int): BasicResponse

    suspend fun getWishlist(): List<Wishlist>

    suspend fun getWishlistById(id: Int): Wishlist

    suspend fun removeFromWishlist(id: Int): BasicResponse

}
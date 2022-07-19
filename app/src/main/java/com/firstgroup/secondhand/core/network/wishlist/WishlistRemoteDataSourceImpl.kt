package com.firstgroup.secondhand.core.network.wishlist

import com.firstgroup.secondhand.core.network.wishlist.model.AddWishlistDto
import com.firstgroup.secondhand.core.network.wishlist.model.DeleteWishlistDto
import com.firstgroup.secondhand.core.network.wishlist.model.WishlistDto
import com.firstgroup.secondhand.core.network.wishlist.retrofit.WishlistService
import javax.inject.Inject

class WishlistRemoteDataSourceImpl @Inject constructor(
    private val service: WishlistService
) : WishlistRemoteDataSource {

    override suspend fun addToWishlist(productId: Int): AddWishlistDto {
        return service.addToWishlist(productId)
    }

    override suspend fun getWishlist(): List<WishlistDto> {
        return service.getWishlist()
    }

    override suspend fun getWishlistById(id: Int): WishlistDto {
        return service.getWishlistById(id)
    }

    override suspend fun removeFromWishlist(id: Int): DeleteWishlistDto {
        return service.removeFromWishlist(id)
    }

}
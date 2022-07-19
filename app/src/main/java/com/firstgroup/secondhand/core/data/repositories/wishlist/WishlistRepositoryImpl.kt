package com.firstgroup.secondhand.core.data.repositories.wishlist

import com.firstgroup.secondhand.core.model.BasicResponse
import com.firstgroup.secondhand.core.model.Wishlist
import com.firstgroup.secondhand.core.network.wishlist.WishlistRemoteDataSource
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val remoteDataSource: WishlistRemoteDataSource
): WishlistRepository {

    override suspend fun addToWishlist(productId: Int): BasicResponse {
        return remoteDataSource.addToWishlist(productId).mapToDomainModel()
    }

    override suspend fun getWishlist(): List<Wishlist> {
        return remoteDataSource.getWishlist().map {
            it.mapToDomainModel()
        }
    }

    override suspend fun getWishlistById(id: Int): Wishlist {
        return remoteDataSource.getWishlistById(id).mapToDomainModel()
    }

    override suspend fun removeFromWishlist(id: Int): BasicResponse {
        return remoteDataSource.removeFromWishlist(id).mapToDomainModel()
    }

}
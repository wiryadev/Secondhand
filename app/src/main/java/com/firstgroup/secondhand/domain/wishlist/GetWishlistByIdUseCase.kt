package com.firstgroup.secondhand.domain.wishlist

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.wishlist.WishlistRepository
import com.firstgroup.secondhand.core.model.Wishlist
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetWishlistByIdUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<Int, Wishlist>(ioDispatcher) {

    override suspend fun execute(param: Int): Wishlist {
        return wishlistRepository.getWishlistById(param)
    }
    
}
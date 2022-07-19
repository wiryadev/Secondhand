package com.firstgroup.secondhand.domain.wishlist

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.wishlist.WishlistRepository
import com.firstgroup.secondhand.core.model.Wishlist
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetWishlistUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<Any, List<Wishlist>>(ioDispatcher) {

    override suspend fun execute(param: Any): List<Wishlist> {
        return wishlistRepository.getWishlist()
    }

}
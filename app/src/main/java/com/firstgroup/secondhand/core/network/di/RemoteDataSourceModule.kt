package com.firstgroup.secondhand.core.network.di

import com.firstgroup.secondhand.core.network.auth.AuthRemoteDataSource
import com.firstgroup.secondhand.core.network.auth.AuthRemoteDataSourceImpl
import com.firstgroup.secondhand.core.network.history.HistoryRemoteDataSource
import com.firstgroup.secondhand.core.network.history.HistoryRemoteDataSourceImpl
import com.firstgroup.secondhand.core.network.notification.NotificationRemoteDataSource
import com.firstgroup.secondhand.core.network.notification.NotificationRemoteDataSourceImpl
import com.firstgroup.secondhand.core.network.order.OrderRemoteDataSource
import com.firstgroup.secondhand.core.network.order.OrderRemoteDataSourceImpl
import com.firstgroup.secondhand.core.network.product.ProductRemoteDataSource
import com.firstgroup.secondhand.core.network.product.ProductRemoteDataSourceImpl
import com.firstgroup.secondhand.core.network.wishlist.WishlistRemoteDataSource
import com.firstgroup.secondhand.core.network.wishlist.WishlistRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDataSourceModule {

    @Binds
    @Singleton
    fun bindsAuthRemoteDataSource(
        authRemoteDataSource: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    fun bindsProductRemoteDataSource(
        productRemoteDataSourceImpl: ProductRemoteDataSourceImpl
    ): ProductRemoteDataSource

    @Binds
    @Singleton
    fun bindsOrderRemoteDataSource(
        orderRemoteDataSourceImpl: OrderRemoteDataSourceImpl
    ): OrderRemoteDataSource

    @Binds
    @Singleton
    fun bindsNotificationRemoteDataSource(
        notificationRemoteDataSourceImpl: NotificationRemoteDataSourceImpl
    ): NotificationRemoteDataSource

    @Binds
    @Singleton
    fun bindsHistoryRemoteDataSource(
        historyRemoteDataSourceImpl: HistoryRemoteDataSourceImpl
    ): HistoryRemoteDataSource

    @Binds
    @Singleton
    fun bindsWishlistRemoteDataSource(
        wishlistRemoteDataSourceImpl: WishlistRemoteDataSourceImpl
    ): WishlistRemoteDataSource

}
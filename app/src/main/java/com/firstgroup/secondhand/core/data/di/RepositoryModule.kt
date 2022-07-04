package com.firstgroup.secondhand.core.data.di

import com.firstgroup.secondhand.core.data.repositories.auth.AuthRepository
import com.firstgroup.secondhand.core.data.repositories.auth.AuthRepositoryImpl
import com.firstgroup.secondhand.core.data.repositories.notification.NotificationRepository
import com.firstgroup.secondhand.core.data.repositories.notification.NotificationRepositoryImpl
import com.firstgroup.secondhand.core.data.repositories.order.OrderRepository
import com.firstgroup.secondhand.core.data.repositories.order.OrderRepositoryImpl
import com.firstgroup.secondhand.core.data.repositories.product.ProductRepository
import com.firstgroup.secondhand.core.data.repositories.product.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    fun bindsProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    fun bindsOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository

    @Binds
    @Singleton
    fun bindsNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository

}
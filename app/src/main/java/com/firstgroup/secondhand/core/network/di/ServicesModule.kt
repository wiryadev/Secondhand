package com.firstgroup.secondhand.core.network.di

import com.firstgroup.secondhand.core.network.auth.retrofit.AuthService
import com.firstgroup.secondhand.core.network.history.retrofit.HistoryService
import com.firstgroup.secondhand.core.network.notification.retrofit.NotificationService
import com.firstgroup.secondhand.core.network.order.retrofit.OrderService
import com.firstgroup.secondhand.core.network.product.retrofit.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService = retrofit.create()

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): ProductService = retrofit.create()


    @Provides
    @Singleton
    fun provideOrderService(retrofit: Retrofit): OrderService = retrofit.create()

    @Provides
    @Singleton
    fun provideNotificationService(retrofit: Retrofit): NotificationService = retrofit.create()

    @Provides
    @Singleton
    fun provideHistoryService(retrofit: Retrofit): HistoryService = retrofit.create()

}
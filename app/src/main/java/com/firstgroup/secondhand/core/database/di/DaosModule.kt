package com.firstgroup.secondhand.core.database.di

import com.firstgroup.secondhand.core.database.SecondhandDatabase
import com.firstgroup.secondhand.core.database.product.dao.CategoryDao
import com.firstgroup.secondhand.core.database.product.dao.ProductCacheDao
import com.firstgroup.secondhand.core.database.product.dao.WishlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    @Singleton
    fun provideProductCacheDao(
        database: SecondhandDatabase
    ): ProductCacheDao = database.productCacheDao()

    @Provides
    @Singleton
    fun provideCategoryDao(
        database: SecondhandDatabase
    ): CategoryDao = database.categoryDao()

    @Provides
    @Singleton
    fun provideWishlistDao(
        database: SecondhandDatabase
    ): WishlistDao = database.wishlistDao()


}
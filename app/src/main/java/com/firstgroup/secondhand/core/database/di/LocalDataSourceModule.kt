package com.firstgroup.secondhand.core.database.di

import com.firstgroup.secondhand.core.database.product.ProductLocalDataSource
import com.firstgroup.secondhand.core.database.product.ProductLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataSourceModule {

    @Binds
    @Singleton
    fun bindsProductLocalDataSource(
        productLocalDataSourceImpl: ProductLocalDataSourceImpl
    ): ProductLocalDataSource

}
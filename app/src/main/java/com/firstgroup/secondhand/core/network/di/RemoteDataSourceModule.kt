package com.firstgroup.secondhand.core.network.di

import com.firstgroup.secondhand.core.network.auth.AuthRemoteDataSource
import com.firstgroup.secondhand.core.network.auth.AuthRemoteDataSourceImpl
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

}
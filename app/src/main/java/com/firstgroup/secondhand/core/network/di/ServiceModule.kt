package com.firstgroup.secondhand.core.network.di

import com.firstgroup.secondhand.core.network.auth.retrofit.AuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): AuthApiService {
        return retrofit.create()
    }

}
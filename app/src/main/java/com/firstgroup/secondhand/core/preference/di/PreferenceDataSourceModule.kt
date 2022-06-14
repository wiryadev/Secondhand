package com.firstgroup.secondhand.core.preference.di

import com.firstgroup.secondhand.core.preference.AuthPreferenceDataSource
import com.firstgroup.secondhand.core.preference.AuthPreferenceDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PreferenceDataSourceModule {

    @Binds
    fun bindsAuthPreferenceDataSource(
        authPreferenceDataSourceImpl: AuthPreferenceDataSourceImpl
    ): AuthPreferenceDataSource

}
package com.firstgroup.secondhand.core.database.di

import android.content.Context
import androidx.room.Room
import com.firstgroup.secondhand.core.database.SecondhandDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesNiaDatabase(
        @ApplicationContext context: Context,
    ): SecondhandDatabase = Room
        .databaseBuilder(
            context,
            SecondhandDatabase::class.java,
            "secondhand_db"
        )
        .build()

}
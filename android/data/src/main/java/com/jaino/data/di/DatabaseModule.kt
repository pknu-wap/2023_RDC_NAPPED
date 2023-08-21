package com.jaino.data.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.jaino.data.database.FavoriteDatabase
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
    fun providesFavoriteDatabase(
        @ApplicationContext context: Context
    ): FavoriteDatabase {
        return databaseBuilder(
            context = context,
            klass = FavoriteDatabase::class.java,
            name = "favorite_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesUserInfoDao(dataBase: FavoriteDatabase) = dataBase.favoriteDao
}
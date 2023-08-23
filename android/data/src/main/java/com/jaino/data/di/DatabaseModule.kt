package com.jaino.data.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.jaino.data.database.NappedDatabase
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
    fun providesNappedDatabase(
        @ApplicationContext context: Context
    ): NappedDatabase {
        return databaseBuilder(
            context = context,
            klass = NappedDatabase::class.java,
            name = "favorite_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesFavoriteDao(dataBase: NappedDatabase) = dataBase.favoriteDao

    @Provides
    @Singleton
    fun providesEmploymentDao(dataBase: NappedDatabase) = dataBase.employmentDao

    @Provides
    @Singleton
    fun providesRemoteKeyDao(dataBase: NappedDatabase) = dataBase.remoteKeyDao
}
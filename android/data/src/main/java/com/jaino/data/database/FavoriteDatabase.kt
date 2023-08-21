package com.jaino.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class FavoriteDatabase: RoomDatabase() {
    abstract val favoriteDao: FavoriteDao
}
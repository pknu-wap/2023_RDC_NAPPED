package com.jaino.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jaino.data.database.dao.EmploymentDao
import com.jaino.data.database.dao.FavoriteDao
import com.jaino.data.database.dao.RemoteKeyDao
import com.jaino.data.model.local.EmploymentEntity
import com.jaino.data.model.local.FavoriteEntity
import com.jaino.data.model.local.RemoteKeyEntity


@Database(
    entities = [FavoriteEntity::class, EmploymentEntity::class, RemoteKeyEntity::class],
    version = 1
)
abstract class NappedDatabase: RoomDatabase() {
    abstract val favoriteDao: FavoriteDao
    abstract val employmentDao: EmploymentDao
    abstract val remoteKeyDao: RemoteKeyDao
}
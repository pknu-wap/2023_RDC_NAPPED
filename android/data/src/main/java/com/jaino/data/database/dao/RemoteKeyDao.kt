package com.jaino.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jaino.data.model.local.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Upsert
    suspend fun insertRemoteKeyList(remoteKey: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_keys WHERE repoId = :id")
    suspend fun getRemoteKeyById(id: Long): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKey()
}
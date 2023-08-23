package com.jaino.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey val repoId: Long,
    val nextKey: Int?
)

package com.jaino.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jaino.domain.model.Favorite

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey val company: String,
    val kind: String,
    val location: String
){

    fun toDomain() = Favorite(
        company = company,
        kind = kind,
        location = location
    )
}
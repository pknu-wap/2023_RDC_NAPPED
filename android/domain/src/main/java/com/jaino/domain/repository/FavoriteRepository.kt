package com.jaino.domain.repository

import com.jaino.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun getFavoriteList(): Result<Flow<List<Favorite>>>

    suspend fun insertFavorite(favorite: Favorite): Result<Unit>

    suspend fun deleteFavorites(favorite: Favorite): Result<Unit>
}

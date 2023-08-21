package com.jaino.data.repository

import com.jaino.data.database.FavoriteDao
import com.jaino.data.database.FavoriteEntity
import com.jaino.domain.model.Favorite
import com.jaino.domain.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao,
    private val dispatcher: CoroutineDispatcher
): FavoriteRepository {
    override suspend fun getFavoriteList(): Result<Flow<List<Favorite>>> {
        return withContext(dispatcher){
            runCatching {
                dao.getFavoriteList().map { entityList ->
                    entityList.map{ it.toDomain() }
                }
            }
        }
    }

    override suspend fun insertFavorite(favorite: Favorite): Result<Unit> {
        return withContext(dispatcher){
            runCatching {
                dao.insertFavorite(
                    favorite.toEntity()
                )
            }
        }
    }

    override suspend fun deleteFavorites(favorite: Favorite): Result<Unit> {
        return withContext(dispatcher){
            runCatching {
                dao.deleteFavorite(
                    favorite.toEntity()
                )
            }
        }
    }

    private fun Favorite.toEntity() = FavoriteEntity(
        company = company,
        kind = kind,
        location = location
    )
}
package com.jaino.data.repository

import com.jaino.data.database.dao.FavoriteDao
import com.jaino.data.model.local.FavoriteEntity
import com.jaino.domain.model.Favorite
import com.jaino.domain.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao,
    private val dispatcher: CoroutineDispatcher
): FavoriteRepository {
    override fun getFavoriteList(): Flow<Result<List<Favorite>>> {
        return dao.getFavoriteList()
            .map { list -> Result.success(list.map{ it.toDomain() }) }
            .catch{ e -> emit(Result.failure(e)) }
            .flowOn(dispatcher)
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
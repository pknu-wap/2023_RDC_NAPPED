package com.jaino.data.service

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.jaino.data.database.NappedDatabase
import com.jaino.data.model.local.EmploymentEntity
import com.jaino.data.model.local.RemoteKeyEntity
import com.jaino.data.model.remote.toEntity
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class EmploymentRemoteMediator(
    private val database: NappedDatabase,
    private val service: EmploymentService
): RemoteMediator<Int, EmploymentEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EmploymentEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> DEFAULT_PAGE_INDEX
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastData = getPageLastData(state)
                    if (lastData == null) {
                        DEFAULT_PAGE_INDEX
                    }
                    else {
                        val nextKey = getRemoteKey(lastData.number)?.nextKey
                            ?: return MediatorResult.Success(endOfPaginationReached = true)
                        nextKey
                    }
                }

            }

            val employmentResponses = service.getEmploymentList(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            database.withTransaction {
                if(loadType == LoadType.REFRESH){
                    database.employmentDao.clearEmploymentList()
                    database.remoteKeyDao.clearRemoteKey()
                }

                val employmentEntities = employmentResponses.map{ it.toEntity() }
                val nextKey = if (employmentEntities.isEmpty()) null else loadKey + 1
                val keys = employmentEntities.map{
                    RemoteKeyEntity(it.number, nextKey)
                }

                database.employmentDao.insertEmploymentList(employmentEntities)
                database.remoteKeyDao.insertRemoteKeyList(keys)
            }

            MediatorResult.Success(
                endOfPaginationReached = employmentResponses.isEmpty()
            )
        }
        catch(e: IOException){
            MediatorResult.Error(e)
        }
        catch(e: ClientRequestException){
            MediatorResult.Error(e)
        }
        catch(e: ServerResponseException){
            MediatorResult.Error(e)
        }
    }

    private fun getPageLastData(
        state: PagingState<Int, EmploymentEntity>
    ): EmploymentEntity?{
        val lastPage = state.pages.lastOrNull()
        if(lastPage == null){
            return lastPage
        }

        val pageLastData = lastPage.data.lastOrNull()
        return pageLastData
    }

    private suspend fun getRemoteKey(id: Long): RemoteKeyEntity? {
        return database.withTransaction {
            database.remoteKeyDao.getRemoteKeyById(id)
        }
    }

    companion object{
        private const val DEFAULT_PAGE_INDEX = 1
    }
}
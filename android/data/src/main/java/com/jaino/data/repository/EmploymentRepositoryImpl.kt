package com.jaino.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.jaino.data.database.dao.EmploymentDao
import com.jaino.data.model.local.EmploymentEntity
import com.jaino.domain.model.Employment
import com.jaino.domain.repository.EmploymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EmploymentRepositoryImpl @Inject constructor(
    private val employmentPager: Pager<Int, EmploymentEntity>,
    private val dao: EmploymentDao
): EmploymentRepository {

    override suspend fun getEmploymentList(): Flow<PagingData<Employment>> {
        return employmentPager.flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun getEmploymentById(id: Long): Result<Employment> {
        return runCatching {
            dao.getEmploymentById(id).toDomain()
        }
    }
}
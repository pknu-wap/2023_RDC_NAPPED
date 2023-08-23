package com.jaino.domain.repository

import androidx.paging.PagingData
import com.jaino.domain.model.Employment
import kotlinx.coroutines.flow.Flow

interface EmploymentRepository {
    suspend fun getEmploymentList(): Flow<PagingData<Employment>>

    suspend fun getEmploymentById(id: Long): Result<Employment>
}
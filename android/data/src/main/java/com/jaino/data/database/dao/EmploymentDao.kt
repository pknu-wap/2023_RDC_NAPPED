package com.jaino.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jaino.data.model.local.EmploymentEntity

@Dao
interface EmploymentDao {
    @Upsert
    suspend fun insertEmploymentList(employmentList: List<EmploymentEntity>)

    @Query("SELECT * FROM employment")
    fun employmentPagingSource(): PagingSource<Int, EmploymentEntity>

    @Query("DELETE FROM employment")
    suspend fun clearEmploymentList()

    @Query("SELECT * FROM employment WHERE number = :id")
    suspend fun getEmploymentById(id: Long): EmploymentEntity
}
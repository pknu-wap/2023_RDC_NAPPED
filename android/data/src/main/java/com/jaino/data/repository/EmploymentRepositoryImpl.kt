package com.jaino.data.repository

import com.jaino.data.model.toDomain
import com.jaino.data.service.EmploymentService
import com.jaino.domain.model.Employment
import com.jaino.domain.repository.EmploymentRepository
import javax.inject.Inject

class EmploymentRepositoryImpl @Inject constructor(
    private val employmentService: EmploymentService
): EmploymentRepository {

    override suspend fun getEmploymentList(pageCount: Int, page: Int): Result<List<Employment>> {
        return employmentService.getEmploymentList(pageCount, page).mapCatching { list ->
            list.map{ it.toDomain() }
        }
    }
}
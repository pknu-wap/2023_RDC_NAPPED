package com.jaino.domain.repository

import com.jaino.domain.model.Employment

interface EmploymentRepository {
    suspend fun getEmploymentList(pageCount: Int, page: Int): Result<List<Employment>>
}
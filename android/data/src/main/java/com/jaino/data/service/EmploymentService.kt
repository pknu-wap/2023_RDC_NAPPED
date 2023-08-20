package com.jaino.data.service

import com.jaino.data.model.EmploymentResponse

interface EmploymentService {
    suspend fun getEmploymentList(pageCount: Int, page: Int): Result<List<EmploymentResponse>>
}
package com.jaino.data.service

import com.jaino.data.model.remote.EmploymentResponse

interface EmploymentService {
    suspend fun getEmploymentList(pageCount: Int, page: Int): List<EmploymentResponse>
}
package com.jaino.domain.repository

import com.jaino.domain.model.Company

interface CompanyRepository {
    suspend fun getCompanyList(): Result<List<Company>>
}
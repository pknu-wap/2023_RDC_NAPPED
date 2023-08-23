package com.jaino.data.service

import com.jaino.data.model.remote.EmploymentAllResponse
import com.jaino.data.model.remote.EmploymentResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class EmploymentServiceImpl @Inject constructor(
    private val client: HttpClient
): EmploymentService {
    override suspend fun getEmploymentList(pageCount: Int, page: Int): List<EmploymentResponse> {
        val response = client.get(""){
            url{
                parameters.append("numOfRows", pageCount.toString())
                parameters.append("pageNo", page.toString())
            }
        }.body<EmploymentAllResponse>()
        return response.body.items.item
    }
}

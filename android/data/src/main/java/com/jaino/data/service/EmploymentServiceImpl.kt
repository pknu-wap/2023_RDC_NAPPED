package com.jaino.data.service

import com.jaino.data.model.EmploymentAllResponse
import com.jaino.data.model.EmploymentResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import timber.log.Timber
import javax.inject.Inject

class EmploymentServiceImpl @Inject constructor(
    private val client: HttpClient
): EmploymentService {
    override suspend fun getEmploymentList(pageCount: Int, page: Int): Result<List<EmploymentResponse>> {
        return runCatching {

            val response = client.get(""){
                url{
                    parameters.append("numOfRows", pageCount.toString())
                    parameters.append("pageNo", page.toString())
                }
            }.body<EmploymentAllResponse>()

            response.body.items.item
        }.onFailure { error ->
            Timber.e(error)
            error.printStackTrace()
        }
    }
}

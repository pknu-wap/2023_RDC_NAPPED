package com.jaino.data.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jaino.data.model.CompanyResponse
import com.jaino.data.utils.await
import com.jaino.domain.model.Company
import com.jaino.domain.repository.CompanyRepository
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val database: DatabaseReference
): CompanyRepository {
    override suspend fun getCompanyList(): Result<List<Company>> {
        return runCatching {
            val list = mutableListOf<Company>()
            for (i in 1..10) {
                val databaseReference = database.child("$i")
                val data = databaseReference.get().await().getValue(CompanyResponse::class.java)

                if(data == null)
                    break
                else
                    list.add(data.toDomain())
            }
            list.toList()
        }
    }
}
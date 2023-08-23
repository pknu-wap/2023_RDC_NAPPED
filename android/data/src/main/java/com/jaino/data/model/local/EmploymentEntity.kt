package com.jaino.data.model.local

import androidx.room.Entity
import com.jaino.domain.model.Employment

@Entity(tableName = "employment", primaryKeys = ["number", "company"])
data class EmploymentEntity(
    val number: Long,
    val title: String,
    val company: String,
    val deadline: String,
    val workTime: String,
    val location: String,
    val kind : String,
    val agentKind: String,
    val education: String,
    val career: String,
    val salary: String,
    val work: String,
    val recruitNumber: String
){
    fun toDomain() = Employment(
        number = number,
        title = title,
        company = company,
        deadline = deadline,
        workTime = workTime,
        location = location,
        kind = kind,
        agentKind = agentKind,
        education = education,
        career = career,
        salary = salary,
        work = work,
        recruitNumber = recruitNumber
    )
}

package com.jaino.data.model

import com.jaino.domain.model.Company

data class CompanyResponse(
    val company: String,
    val timeline: Int,
    val kind: String,
    val location: String,
    val scale: String
){
    constructor(): this(
        "",
        0,
        "",
        "",
        ""
    )

    fun toDomain() = Company(
        company = company,
        timeLine = timeline.toString(),
        kind = kind,
        location = location,
        scale = scale
    )
}
package com.jaino.data.model.remote

import com.jaino.domain.model.Board

data class BoardResponse(
    val title: String,
    val content: String
){
    constructor(): this(
        "",
        ""
    )

    fun toDomain() = Board(
        title = title,
        content = content
    )
}
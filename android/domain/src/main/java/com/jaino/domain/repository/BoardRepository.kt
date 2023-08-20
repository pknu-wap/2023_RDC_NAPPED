package com.jaino.domain.repository

import com.jaino.domain.model.Board

interface BoardRepository {
    suspend fun getBoardList(): Result<List<Board>>
}
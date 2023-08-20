package com.jaino.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.jaino.data.model.BoardResponse
import com.jaino.data.utils.BOARD_COLLECTION
import com.jaino.data.utils.await
import com.jaino.domain.model.Board
import com.jaino.domain.repository.BoardRepository
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): BoardRepository {

    override suspend fun getBoardList(): Result<List<Board>> {
        return runCatching {
            val list = mutableListOf<Board>()
            for(i in 1 .. 10){
                val document = firestore.collection(BOARD_COLLECTION).document("$i")
                val data = document.get().await().toObject(BoardResponse::class.java)
                if(data == null)
                    break
                else
                    list.add(data.toDomain())
            }
            list
        }
    }
}
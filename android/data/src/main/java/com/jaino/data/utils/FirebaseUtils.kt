package com.jaino.data.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            if (it.exception != null) {
                cont.resumeWithException(it.exception!!)
            } else {
                cont.resume(it.result, null)
            }
        }
    }
}

const val BOARD_COLLECTION = "Board"
const val FIRST_CHILD_PATH = "/1ap-c7JBEkTS6DsyPeesuDvmZ3XnZvpyDFSaqpxxbYUM"
const val SECOND_CHILD_PATH = "시트1"
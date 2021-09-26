package com.komeyama.asyncpractice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoroutinePractice {

    sealed class Result<out R> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
    }

    data class SimpleResult(val array: List<String>)

    suspend fun simpleRequest(isRequest: Boolean): Result<SimpleResult> {
        return withContext(Dispatchers.IO) {
            if (isRequest) {
                Result.Success(SimpleResult(listOf("1", "2", "3", "4", "5")))
            } else {
                Result.Error(Exception("simple result is error!"))
            }
        }
    }
}
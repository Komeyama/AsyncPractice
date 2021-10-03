package com.komeyama.asyncpractice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class CoroutinePractice {

    sealed class Result<out R> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
    }

    data class SimpleResult(val array: List<String>)

    private val _stateFlow = MutableStateFlow("0")
    val stateFlow: StateFlow<String> = _stateFlow
    private val _sharedFlow = MutableStateFlow("0")
    val sharedFlow: SharedFlow<String> = _sharedFlow

    suspend fun simpleRequest(isRequest: Boolean): Result<SimpleResult> {
        return withContext(Dispatchers.IO) {
            if (isRequest) {
                Result.Success(SimpleResult(listOf("1", "2", "3", "4", "5")))
            } else {
                Result.Error(Exception("simple result is error!"))
            }
        }
    }

    fun simpleFlowEmit() {
        listOf("1", "2", "3", "4", "5").forEach {
            _stateFlow.value = it
        }
    }

    fun simpleSharedFlow() {
        listOf("1", "2", "3", "4", "5").forEach {
            _sharedFlow.value = it
        }
    }
}
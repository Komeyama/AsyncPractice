package com.komeyama.asyncpractice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber

class SimpleViewModel : ViewModel() {

    private val coroutinePractice = CoroutinePractice()

    fun simpleRequest(isRequest: Boolean) {
        viewModelScope.launch {
            val result = coroutinePractice.simpleRequest(isRequest)
            Timber.d("simple request result: $result")
        }
    }
}
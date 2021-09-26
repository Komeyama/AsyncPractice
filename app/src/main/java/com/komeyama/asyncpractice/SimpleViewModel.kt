package com.komeyama.asyncpractice

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import timber.log.Timber

class SimpleViewModel : ViewModel() {

    private val coroutinePractice = CoroutinePractice()
    private val _resultLivaData: MutableLiveData<CoroutinePractice.Result<CoroutinePractice.SimpleResult>> = MutableLiveData()
    val resultLivaData: LiveData<CoroutinePractice.Result<CoroutinePractice.SimpleResult>> = _resultLivaData

    fun simpleRequest(isRequest: Boolean) {
        viewModelScope.launch {
            val result = coroutinePractice.simpleRequest(isRequest)
            _resultLivaData.value = result
            Timber.d("simple request result: $result")
        }
    }
}
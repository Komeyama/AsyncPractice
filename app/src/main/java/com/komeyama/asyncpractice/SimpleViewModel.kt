package com.komeyama.asyncpractice

import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class SimpleViewModel : ViewModel() {

    private val coroutinePractice = CoroutinePractice()
    private val _resultLivaData: MutableLiveData<CoroutinePractice.Result<CoroutinePractice.SimpleResult>> =
        MutableLiveData()
    val resultLivaData: LiveData<CoroutinePractice.Result<CoroutinePractice.SimpleResult>> =
        _resultLivaData
    val flowStringList = mutableListOf<String>()

    fun simpleRequest(isRequest: Boolean) {
        viewModelScope.launch {
            val result = coroutinePractice.simpleRequest(isRequest)
            _resultLivaData.value = result
            Timber.d("simple request result: $result")
        }
    }

    fun simpleFlow() {
        viewModelScope.launch {
            coroutinePractice.stateFlow.collect { result ->
                Timber.d("simple flow result: $result")
                flowStringList.add(result)
            }
        }
        coroutinePractice.simpleFlowEmit()
    }
}
package com.komeyama.asyncpractice

import io.reactivex.rxjava3.core.Observable

class ReactivePractice {

    fun observableStringList(): Observable<String> {
        return Observable.create { emitter ->
            val messages = listOf("hello!", "hello!!", "hello!!!", "hello!!!!", "hello!!!!!")
            messages.forEach { message ->
                if (emitter.isDisposed) return@create
                emitter.onNext(message)
            }
            emitter.onComplete()
        }
    }
}
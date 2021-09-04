package com.komeyama.asyncpractice

import io.reactivex.rxjava3.core.*

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

    fun flowableStringList(backpressureType: BackpressureStrategy): Flowable<String> {
        return Flowable.create({ emitter ->
            val messages = listOf(
                "hello world!",
                "hello world!!",
                "hello world!!!",
                "hello world!!!!",
                "hello world!!!!!"
            )
            messages.forEach { message ->
                if (emitter.isCancelled) return@create
                emitter.onNext(message)
            }
            emitter.onComplete()
        }, backpressureType)
    }
}
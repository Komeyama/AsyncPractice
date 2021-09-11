package com.komeyama.asyncpractice

import io.reactivex.rxjava3.core.*

class ReactivePractice {

    fun observableStringList(): Observable<String> {
        return Observable.create { emitter ->
            val messages = listOf("1", "2", "3", "4", "5")
            messages.forEach { message ->
                if (emitter.isDisposed) return@create
                emitter.onNext(message)
            }
            emitter.onComplete()
        }
    }

    fun singleString(): Single<String> {
        return Single.create { emitter ->
            val message = "1"
            emitter.onSuccess(message)
        }
    }

    fun flowableStringList(backpressureType: BackpressureStrategy): Flowable<String> {
        return Flowable.create({ emitter ->
            val messages = listOf("1", "2", "3", "4", "5")
            messages.forEach { message ->
                if (emitter.isCancelled) return@create
                emitter.onNext(message)
            }
            emitter.onComplete()
        }, backpressureType)
    }
}
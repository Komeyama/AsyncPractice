package com.komeyama.asyncpractice

import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.processors.PublishProcessor
import java.util.concurrent.TimeUnit

class ReactivePractice {

    val publishProcessor: PublishProcessor<String> = PublishProcessor.create()

    init {
        Observable.fromArray(arrayListOf("1", "2", "3", "4", "5"))
            .delay(1000L, TimeUnit.MILLISECONDS).subscribe { messages ->
            messages.forEach {
                publishProcessor.onNext(it)
            }
        }
    }

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

    fun maybeString(message: String = ""): Maybe<String> {
        return Maybe.create { emitter ->
            if (message != "") {
                emitter.onSuccess(message)
            } else {
                emitter.onComplete()
            }
        }
    }

    fun completable(): Completable {
        return Completable.create { emitter ->
            emitter.onComplete()
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
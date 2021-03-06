package com.komeyama.asyncpractice

import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.processors.AsyncProcessor
import io.reactivex.rxjava3.processors.BehaviorProcessor
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.processors.ReplayProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ReactivePractice {

    val publishProcessor: PublishProcessor<String> = PublishProcessor.create()
    val behaviorProcessor: BehaviorProcessor<String> = BehaviorProcessor.create()
    val replayProcessor: ReplayProcessor<String> = ReplayProcessor.createWithTime(2000, TimeUnit.MILLISECONDS, Schedulers.computation())
    val asyncProcessor: AsyncProcessor<String> = AsyncProcessor.create()

    init {
        setupPublishProcessorDemo()
        setupBehaviorProcessorDemo()
        setupReplayProcessorDemo()
        setupAsyncProcessorDemo()
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

    private fun setupPublishProcessorDemo() {
        Observable.fromArray(arrayListOf("1", "2", "3", "4", "5"))
            .delay(1000L, TimeUnit.MILLISECONDS).subscribe { messages ->
                messages.forEach {
                    publishProcessor.onNext(it)
                }
                publishProcessor.onComplete()
            }
    }

    private fun setupBehaviorProcessorDemo() {
        arrayListOf("1", "2", "3", "4", "5").forEach {
            behaviorProcessor.onNext(it)
        }
        Observable.just(0L).delay(3000L, TimeUnit.MILLISECONDS).subscribe {
            behaviorProcessor.onNext("6")
            behaviorProcessor.onNext("7")
            behaviorProcessor.onNext("8")
            behaviorProcessor.onComplete()
        }
    }

    private fun setupReplayProcessorDemo() {
        Observable.fromArray(arrayListOf("1", "2", "3", "4", "5")).subscribe { messages ->
            messages.forEach {
                replayProcessor.onNext(it)
            }
            replayProcessor.onComplete()
        }
    }

    private fun setupAsyncProcessorDemo() {
        arrayListOf("1", "2", "3", "4", "5").forEach {
            asyncProcessor.onNext(it)
        }
        asyncProcessor.onComplete()
    }
}
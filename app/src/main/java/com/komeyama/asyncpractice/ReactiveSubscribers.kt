package com.komeyama.asyncpractice

import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ReactiveSubscribers {

    private val reactivePractice: ReactivePractice = ReactivePractice()

    val observableStringList = mutableListOf<String>()
    val flowableStringList = mutableListOf<String>()

    fun onObserveObservableStringList() {
        reactivePractice.observableStringList()
            .subscribe(object : DisposableObserver<String>() {
                override fun onNext(t: String?) {
                    Timber.d("observable message: $t")
                    if (t != null) {
                        observableStringList.add(t)
                    }
                }

                override fun onError(e: Throwable?) {
                    Timber.d("observable message error: ${e?.message}")
                }

                override fun onComplete() {
                    Timber.d("observable message complete!")
                }
            })
    }

    fun onObserveFlowableStringList() {
        reactivePractice.flowableStringList(BackpressureStrategy.LATEST)
            .subscribe(object : Subscriber<String> {
                private var subscription: Subscription? = null
                override fun onSubscribe(s: Subscription?) {
                    subscription = s
                    subscription?.request(1L)
                }

                override fun onNext(t: String?) {
                    Timber.d("flowable message: $t")
                    if (t != null) {
                        flowableStringList.add(t)
                    }
                    Observable.just(0).delay(1000L, TimeUnit.MILLISECONDS).subscribe {
                        subscription?.request(1L)
                    }
                }

                override fun onError(e: Throwable?) {
                    Timber.d("flowable message error: ${e?.message}")
                }

                override fun onComplete() {
                    Timber.d("flowable message complete!")
                }
            })
    }
}
package com.komeyama.asyncpractice

import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.subscribers.ResourceSubscriber
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ReactiveSubscribers {

    private val reactivePractice: ReactivePractice = ReactivePractice()

    val observableStringList = mutableListOf<String>()
    var singleString = ""
    var maybeString = ""
    var isCompletable = false
    val flowableStringList = mutableListOf<String>()
    val publishProcessorList = mutableListOf<String>()
    val behaviorProcessorList = mutableListOf<String>()
    val replayProcessorList = mutableListOf<String>()

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

    fun onObserveSingleString() {
        reactivePractice.singleString().subscribe(object : DisposableSingleObserver<String>() {
            override fun onSuccess(t: String?) {
                Timber.d("single message: $t")
                if (t != null) {
                    singleString = t
                }
            }

            override fun onError(e: Throwable?) {
                Timber.d("single message error: ${e?.message}")
            }
        })
    }

    fun onObserveMaybeString(message: String = "") {
        reactivePractice.maybeString(message).subscribe(object : MaybeObserver<String> {
            override fun onSubscribe(d: Disposable?) {}

            override fun onSuccess(t: String?) {
                Timber.d("maybe message: $t")
                if (t != null) {
                    maybeString = t
                }
            }

            override fun onError(e: Throwable?) {
                Timber.d("maybe message error: ${e?.message}")
            }

            override fun onComplete() {
                maybeString = "0"
                Timber.d("maybe complete!")
            }
        })
    }

    fun onObserveCompletable() {
        reactivePractice.completable().subscribe(object : CompletableObserver {
            override fun onSubscribe(d: Disposable?) {}

            override fun onComplete() {
                isCompletable = true
                Timber.d("completable complete!")
            }

            override fun onError(e: Throwable?) {
                Timber.d("completable message error: ${e?.message}")
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

    fun onObservePublishProcessorList() {
        reactivePractice.publishProcessor.subscribe(object : ResourceSubscriber<String>() {
            override fun onNext(t: String?) {
                Timber.d("publish processor message: $t")
                t?.let {
                    publishProcessorList.add(it)
                }
            }

            override fun onError(t: Throwable?) {
                Timber.d("publish processor message error: ${t?.message}")
            }

            override fun onComplete() {
                Timber.d("publish processor complete!")
            }
        })
    }

    fun onObserveBehaviorProcessorList() {
        reactivePractice.behaviorProcessor.subscribe(object : ResourceSubscriber<String>() {
            override fun onNext(t: String?) {
                Timber.d("behavior processor message: $t")
                t?.let {
                    behaviorProcessorList.add(it)
                }
            }

            override fun onError(t: Throwable?) {
                Timber.d("behavior processor message error: ${t?.message}")
            }

            override fun onComplete() {
                Timber.d("behavior processor complete!")
            }
        })
    }

    fun onObserveReplayProcessorList() {
        reactivePractice.replayProcessor.subscribe(object : ResourceSubscriber<String>() {
            override fun onNext(t: String?) {
                Timber.d("replay processor message: $t")
                t?.let {
                    replayProcessorList.add(it)
                }
            }

            override fun onError(t: Throwable?) {
                Timber.d("replay processor message error: ${t?.message}")
            }

            override fun onComplete() {
                Timber.d("replay processor complete!")
            }
        })
    }
}
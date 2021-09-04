package com.komeyama.asyncpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.rxjava3.observers.DisposableObserver
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val reactivePractice: ReactivePractice = ReactivePractice()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onObserveObservableStringList()
    }

    private fun onObserveObservableStringList() {
        reactivePractice.observableStringList()
            .subscribe(object : DisposableObserver<String>() {
                override fun onNext(t: String?) {
                    Timber.d("observable message: $t")
                }

                override fun onError(e: Throwable?) {
                    Timber.d("observable message error: ${e?.message}")
                }

                override fun onComplete() {
                    Timber.d("observable message complete!")
                }
            })
    }
}
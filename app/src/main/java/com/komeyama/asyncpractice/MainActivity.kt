package com.komeyama.asyncpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private val subscribers: ReactiveSubscribers = ReactiveSubscribers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribers.onObserveObservableStringList()
        subscribers.onObserveSingleString()
        subscribers.onObserveMaybeString("1")
        subscribers.onObserveMaybeString()
        subscribers.onObserveCompletable()
        subscribers.onObserveFlowableStringList()
        subscribers.onObservePublishProcessorString()
    }



}
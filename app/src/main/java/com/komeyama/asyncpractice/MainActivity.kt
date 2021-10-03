package com.komeyama.asyncpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private val subscribers: ReactiveSubscribers = ReactiveSubscribers()
    private val simpleViewModel: SimpleViewModel = SimpleViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribers.onObserveObservableStringList()
        subscribers.onObserveSingleString()
        subscribers.onObserveMaybeString("1")
        subscribers.onObserveMaybeString()
        subscribers.onObserveCompletable()
        subscribers.onObserveFlowableStringList()
        subscribers.onObservePublishProcessorList()
        subscribers.onObserveBehaviorProcessorList()
        subscribers.onObserveReplayProcessorList()
        subscribers.onObserveAsyncProcessorList()

        simpleViewModel.simpleRequest(true)
        simpleViewModel.simpleFlow()
        simpleViewModel.simpleSharedFlow()
    }
}
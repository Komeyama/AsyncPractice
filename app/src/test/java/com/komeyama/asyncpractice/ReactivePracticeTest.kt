package com.komeyama.asyncpractice

import org.junit.Assert
import org.junit.Test

class ReactivePracticeTest {

    private val subscribers: ReactiveSubscribers = ReactiveSubscribers()

    @Test
    fun observableFlowIsCorrect() {
        subscribers.onObserveObservableStringList()
        Assert.assertTrue(
            mutableListOf(
                "1",
                "2",
                "3",
                "4",
                "5"
            ) == subscribers.observableStringList
        )
    }

    @Test
    fun singleFlowIsCorrect() {
        subscribers.onObserveSingleString()
        Assert.assertTrue("1" == subscribers.singleString)
    }

    @Test
    fun maybeFloeIsSuccess() {
        subscribers.onObserveMaybeString("1")
        Assert.assertTrue("1" == subscribers.maybeString)
    }

    @Test
    fun maybeFloeIsCompleted() {
        subscribers.onObserveMaybeString()
        Assert.assertTrue("0" == subscribers.maybeString)
    }

    @Test
    fun flowableFlowIsCorrect() {
        subscribers.onObserveFlowableStringList()
        Thread.sleep(1500L)
        Assert.assertTrue(
            mutableListOf(
                "1",
                "5"
            ) == subscribers.flowableStringList
        )
    }
}
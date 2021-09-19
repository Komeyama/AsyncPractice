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
    fun maybeFloeIsCorrect() {
        subscribers.onObserveMaybeString("1")
        Assert.assertTrue("1" == subscribers.maybeString)
    }

    @Test
    fun maybeFloeIsCompleted() {
        subscribers.onObserveMaybeString()
        Assert.assertTrue("0" == subscribers.maybeString)
    }

    @Test
    fun completableIsSuccess() {
        subscribers.onObserveCompletable()
        Assert.assertTrue(subscribers.isCompletable)
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

    @Test
    fun publishProcessorFlowIsCorrect() {
        subscribers.onObservePublishProcessorList()
        Thread.sleep(2000L)
        println(subscribers.publishProcessorList)
        Assert.assertTrue(
            mutableListOf(
                "1",
                "2",
                "3",
                "4",
                "5"
            ) == subscribers.publishProcessorList
        )
    }

    @Test
    fun behaviorProcessorFlowIsCorrect() {
        // Already notified 1,2,3,4,5, so only 5 is acquired.
        Thread.sleep(2000)
        subscribers.onObserveBehaviorProcessorList()
        // 6,7,8 and wait for the completion notification
        Thread.sleep(1000)
        Assert.assertTrue(
            mutableListOf(
                "5",
                "6",
                "7",
                "8"
            ) == subscribers.behaviorProcessorList
        )
    }
}
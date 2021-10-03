package com.komeyama.asyncpractice

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertEquals

class SimpleViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun simpleRequestIsSuccess() = runBlockingTest {
        val simpleViewModel = SimpleViewModel()
        simpleViewModel.simpleRequest(true)
        assertEquals(
            CoroutinePractice.Result.Success(
                CoroutinePractice.SimpleResult(listOf("1", "2", "3", "4", "5"))
            ),
            LiveDataTestUtil.getValue(simpleViewModel.resultLivaData)
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun simpleRequestIsError() = runBlockingTest {
        val simpleViewModel = SimpleViewModel()
        simpleViewModel.simpleRequest(false)
        assertEquals(
            CoroutinePractice.Result.Error(Exception("simple result is error!")).toString(),
            LiveDataTestUtil.getValue(simpleViewModel.resultLivaData).toString()
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun simpleFlowIsSuccess() = runBlockingTest {
        val simpleViewModel = SimpleViewModel()
        simpleViewModel.simpleFlow()
        Assert.assertTrue(
            mutableListOf(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5"
            ) == simpleViewModel.flowStringList
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun simpleSharedFlowIsSuccess() = runBlockingTest {
        val simpleViewModel = SimpleViewModel()
        simpleViewModel.simpleSharedFlow()
        Assert.assertTrue(
            mutableListOf(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5"
            ) == simpleViewModel.sharedFlowStringList
        )
    }
}
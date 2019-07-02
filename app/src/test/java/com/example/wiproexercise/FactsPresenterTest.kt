package com.example.wiproexercise

import com.example.wiproexercise.activites.contracts.FactsContract
import com.example.wiproexercise.activites.models.FactsModel
import com.example.wiproexercise.activites.models.Row
import com.example.wiproexercise.activites.presenters.FactsPresenter
import com.example.wiproexercise.api.FactsApi
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import java.util.concurrent.Executor
import org.mockito.Mockito.`when` as whenever

class FactsPresenterTest {
    private lateinit var presenter: FactsPresenter
    private lateinit var view: FactsContract.View
    private lateinit var api: FactsApi

    private val immediateScheduler = object : Scheduler() {
        override fun createWorker(): Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
        }
    }

    @Before
    fun setup() {
        RxJavaPlugins.setInitIoSchedulerHandler { immediateScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediateScheduler }

        view = Mockito.mock(FactsContract.View::class.java)
        api = Mockito.mock(FactsApi::class.java)

        presenter = Mockito.spy(FactsPresenter(view, api))
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun testGetFactsApi_errorCase_showError(){
        whenever(api.getFacts()).thenReturn(Single.error(RuntimeException()))

        presenter.callFactsApi()

        verify(view).showError("java.lang.RuntimeException")
    }

    @Test
    fun testGetFactsApi_success_updatesTitle(){
        whenever(api.getFacts()).thenReturn(Single.just(FactsModel(rows = mutableListOf(),title =  "Hello")))

        presenter.callFactsApi()

        verify(view).setTitleText("Hello")
    }

    @Test
    fun testGetFactsApi_success_updatesListData(){
        whenever(api.getFacts()).thenReturn(Single.just(FactsModel(rows = mutableListOf(),title =  "Hello")))

        presenter.callFactsApi()

        verify(view).setListData()
    }

    @Test
    fun testGetFacts_returnsData(){
        val facts = mutableListOf<Row>()
        facts.add(0, Row("","",""))
        facts.add(1, Row("","",""))

        whenever(api.getFacts()).thenReturn(Single.just(FactsModel(rows = facts,title =  "Hello")))

        presenter.callFactsApi()
        presenter.getFacts()

        assert(presenter.getFacts().size == 2)
    }

    @Test
    fun testClearDisposablesisCalled() {
        presenter.clearDisposables()

        verify(presenter).clearDisposables()
    }
}
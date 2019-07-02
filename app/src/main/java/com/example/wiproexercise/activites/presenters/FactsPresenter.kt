package com.example.wiproexercise.activites.presenters

import com.example.wiproexercise.activites.contracts.FactsContract
import com.example.wiproexercise.activites.models.FactsModel
import com.example.wiproexercise.activites.models.Row
import com.example.wiproexercise.api.FactsApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FactsPresenter(private val view: FactsContract.View, private val api: FactsApi) : FactsContract.Presenter {
    private val facts = mutableListOf<Row>()
    private val disposables = CompositeDisposable()

    override fun callFactsApi() {
        Timber.d("calling facts api")

        disposables.add(
            api.getFacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::success, this::handleError)
        )
    }

    private fun success(factsModel: FactsModel) {
        Timber.d("on success")

        facts.clear()
        facts.addAll(factsModel.rows)

        view.setTitleText(factsModel.title)
        view.setListData()
    }

    private fun handleError(error: Throwable) {
        Timber.d("on failure")

        view.showError(error.toString())
    }

    override fun getFacts() = facts

    override fun clearDisposables() {
        Timber.d("clearing disposables")

        disposables.clear()
    }
}
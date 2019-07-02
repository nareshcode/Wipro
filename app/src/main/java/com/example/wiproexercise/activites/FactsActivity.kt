package com.example.wiproexercise.activites

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.wiproexercise.R
import com.example.wiproexercise.activites.adapters.recyclerviews.FactsAdapter
import com.example.wiproexercise.activites.contracts.FactsContract
import com.example.wiproexercise.activites.presenters.FactsPresenter
import com.example.wiproexercise.dagger.components.DaggerFactsComponent
import com.example.wiproexercise.dagger.modules.FactsModule
import kotlinx.android.synthetic.main.facts.*
import timber.log.Timber
import javax.inject.Inject

class FactsActivity : AppCompatActivity(), FactsContract.View {
    @Inject
    lateinit var presenter: FactsPresenter
    @Inject
    lateinit var adapter: FactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.facts)

        DaggerFactsComponent.builder()
            .factsModule(FactsModule(this))
            .build()
            .inject(this)

        facts_list.layoutManager = LinearLayoutManager(this)
        facts_list.adapter = adapter

        callFactsApi()

        swiperefresh.setOnRefreshListener {
            callFactsApi()
        }
    }

    override fun setTitleText(title: String) {
        Timber.d("setting title of page")

        setTitle(title)
    }

    override fun setListData() {
        Timber.d("set facts list data")

        adapter.notifyDataSetChanged()

        swiperefresh.isRefreshing = false
    }

    override fun showError(message: String) {
        Timber.d("showing error")

        Snackbar.make(swiperefresh, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()

        Timber.d("on pause")

        presenter.clearDisposables()
    }

    private fun callFactsApi() {
        if (isNetworkConnected()) {
            Timber.d("online")

            presenter.callFactsApi()
        } else {
            Timber.d("offline")

            showError(getString(R.string.no_interner_conn))
        }
    }

    private fun isNetworkConnected(): Boolean {
        Timber.d("checking internet connection")

        val connectivity = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivity.activeNetworkInfo != null
    }
}

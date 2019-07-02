package com.example.wiproexercise.activites.contracts

import com.example.wiproexercise.activites.models.Row

interface FactsContract {
    interface View{
        fun setTitleText(title: String)
        fun setListData()
        fun showError(message: String)
    }

    interface Presenter{
        fun callFactsApi()
        fun getFacts(): MutableList<Row>
        fun clearDisposables()
    }
}
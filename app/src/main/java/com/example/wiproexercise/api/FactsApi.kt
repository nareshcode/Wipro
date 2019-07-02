package com.example.wiproexercise.api

import com.example.wiproexercise.activites.models.FactsModel
import io.reactivex.Single
import retrofit2.http.GET

interface FactsApi {
    @GET("/s/2iodh4vg0eortkl/facts.json")
    fun getFacts(): Single<FactsModel>
}
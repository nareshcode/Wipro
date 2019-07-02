package com.example.wiproexercise.dagger.modules

import com.example.wiproexercise.activites.FactsActivity
import com.example.wiproexercise.activites.adapters.recyclerviews.FactsAdapter
import com.example.wiproexercise.activites.presenters.FactsPresenter
import com.example.wiproexercise.api.FactsApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class FactsModule(private val activity: FactsActivity) {
    @Provides
    @Singleton
    internal fun providePresenter(factsApi: FactsApi) = FactsPresenter(view = activity, api = factsApi)

    @Provides
    @Singleton
    internal fun provideAdapter(presenter: FactsPresenter) = FactsAdapter(activity, presenter)
}
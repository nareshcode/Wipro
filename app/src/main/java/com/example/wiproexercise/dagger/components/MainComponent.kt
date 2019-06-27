package com.example.wiproexercise.dagger.components

import com.example.wiproexercise.activites.FactsActivity
import com.example.wiproexercise.dagger.modules.FactsPresenterModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FactsPresenterModule::class])
interface MainComponent {
    fun inject(target: FactsActivity)
}
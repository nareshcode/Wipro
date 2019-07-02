package com.example.wiproexercise.dagger.components

import com.example.wiproexercise.activites.FactsActivity
import com.example.wiproexercise.dagger.modules.FactsModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FactsModule::class])
interface FactsComponent {
    fun inject(target: FactsActivity)
}
package com.example.wiproexercise

import android.app.Application
import timber.log.Timber

class FactsApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}
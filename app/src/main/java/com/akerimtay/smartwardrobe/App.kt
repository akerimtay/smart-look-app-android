package com.akerimtay.smartwardrobe

import android.app.Application
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinManager.init(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
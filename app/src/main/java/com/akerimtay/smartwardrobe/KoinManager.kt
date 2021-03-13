package com.akerimtay.smartwardrobe

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object KoinManager {
    fun init(context: Context) {
        startKoin {
            androidContext(context)
            modules(KoinModules.create())
        }
    }
}
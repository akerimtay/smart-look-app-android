package com.akerimtay.smartwardrobe

import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.akerimtay.smartwardrobe.common.persistence.AppPreferences
import com.akerimtay.smartwardrobe.common.persistence.PreferencesContract
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModule : InjectionModule {
    override fun create(): Module = module {
        single<PreferencesContract> { AppPreferences(androidContext()) }
    }
}
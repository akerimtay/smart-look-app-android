package com.akerimtay.smartwardrobe.network

import com.akerimtay.smartwardrobe.common.di.InjectionModule
import org.koin.core.module.Module
import org.koin.dsl.module

object NetworkModule : InjectionModule {
    override fun create(): Module = module {
        single { NetworkManager(get()) }
    }
}
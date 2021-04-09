package com.akerimtay.smartwardrobe.splash

import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.akerimtay.smartwardrobe.splash.ui.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object SplashModule : InjectionModule {
    override fun create(): Module = module {
        viewModel { SplashViewModel(get()) }
    }
}
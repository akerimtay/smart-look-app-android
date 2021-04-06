package com.akerimtay.smartwardrobe.profile

import com.akerimtay.smartwardrobe.common.di.InjectionModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object ProfileModule : InjectionModule {
    override fun create(): Module = module {
        viewModel { ProfileViewModel(get(), get()) }
    }
}
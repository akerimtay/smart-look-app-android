package com.akerimtay.smartwardrobe.articles

import com.akerimtay.smartwardrobe.common.di.InjectionModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object ArticlesModule : InjectionModule {
    override fun create(): Module = module {
        viewModel { ArticlesViewModel() }
    }
}
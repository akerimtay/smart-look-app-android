package com.akerimtay.smartwardrobe.feed

import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.akerimtay.smartwardrobe.feed.ui.FeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object FeedModule : InjectionModule {
    override fun create(): Module = module {
        viewModel { FeedViewModel(get(), get(), get(), get()) }
    }
}
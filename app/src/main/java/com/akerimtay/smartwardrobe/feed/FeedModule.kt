package com.akerimtay.smartwardrobe.feed

import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.akerimtay.smartwardrobe.feed.ui.list.FeedListViewModel
import com.akerimtay.smartwardrobe.feed.ui.main.FeedViewModel
import com.akerimtay.smartwardrobe.user.model.Gender
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object FeedModule : InjectionModule {
    override fun create(): Module = module {
        viewModel { FeedViewModel(get(), get()) }
        viewModel { (gender: Gender) -> FeedListViewModel(gender, get()) }
    }
}
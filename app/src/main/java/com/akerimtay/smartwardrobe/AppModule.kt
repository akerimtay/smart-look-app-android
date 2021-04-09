package com.akerimtay.smartwardrobe

import com.akerimtay.smartwardrobe.common.base.adapter.ContentAdapter
import com.akerimtay.smartwardrobe.common.base.adapter.PagedContentAdapter
import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.akerimtay.smartwardrobe.common.persistence.AppPreferences
import com.akerimtay.smartwardrobe.common.persistence.PreferencesContract
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.feed.FeedViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModule : InjectionModule {
    override fun create(): Module = module {
        single<PreferencesContract> { AppPreferences(androidContext()) }
        factory { ContentAdapter(ItemContentType.values()) }
        factory { PagedContentAdapter(ItemContentType.values()) }
        viewModel { FeedViewModel(get(), get()) }
    }
}
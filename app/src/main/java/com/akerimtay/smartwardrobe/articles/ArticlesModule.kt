package com.akerimtay.smartwardrobe.articles

import com.akerimtay.smartwardrobe.articles.data.ArticleService
import com.akerimtay.smartwardrobe.articles.domain.ArticleRemoteGateway
import com.akerimtay.smartwardrobe.articles.domain.LoadArticlesUseCase
import com.akerimtay.smartwardrobe.articles.ui.ArticlesViewModel
import com.akerimtay.smartwardrobe.common.di.InjectionModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object ArticlesModule : InjectionModule {
    override fun create(): Module = module {
        viewModel { ArticlesViewModel(get()) }

        single<ArticleRemoteGateway> { ArticleService(get()) }

        single { LoadArticlesUseCase(get(), get()) }
    }
}
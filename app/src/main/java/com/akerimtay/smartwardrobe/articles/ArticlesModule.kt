package com.akerimtay.smartwardrobe.articles

import com.akerimtay.smartwardrobe.articles.data.api.ArticleService
import com.akerimtay.smartwardrobe.articles.data.db.ArticleDatabase
import com.akerimtay.smartwardrobe.articles.domain.GetArticleByIdAsFlowUseCase
import com.akerimtay.smartwardrobe.articles.domain.GetArticlesLiveDataUseCase
import com.akerimtay.smartwardrobe.articles.domain.LoadArticlesUseCase
import com.akerimtay.smartwardrobe.articles.domain.gateway.ArticleLocalGateway
import com.akerimtay.smartwardrobe.articles.domain.gateway.ArticleRemoteGateway
import com.akerimtay.smartwardrobe.articles.ui.ArticlesViewModel
import com.akerimtay.smartwardrobe.articles.ui.detail.ArticleDetailViewModel
import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.akerimtay.smartwardrobe.database.AppDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object ArticlesModule : InjectionModule {
    override fun create(): Module = module {
        viewModel { ArticlesViewModel(get(), get()) }
        viewModel { (articleId: Long) -> ArticleDetailViewModel(articleId = articleId, get()) }

        single<ArticleRemoteGateway> { ArticleService(get()) }

        single { get<AppDatabase>().articleDao() }
        single<ArticleLocalGateway> { ArticleDatabase(get()) }

        single { LoadArticlesUseCase(get(), get(), get()) }
        single { GetArticlesLiveDataUseCase(get()) }
        single { GetArticleByIdAsFlowUseCase(get()) }
    }
}
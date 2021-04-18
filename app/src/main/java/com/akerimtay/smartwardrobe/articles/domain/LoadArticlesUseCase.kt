package com.akerimtay.smartwardrobe.articles.domain

import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.network.NetworkManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class LoadArticlesUseCase(
    private val networkManager: NetworkManager,
    private val articleRemoteGateway: ArticleRemoteGateway,
) : UseCase<Unit, Unit>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Unit) {
        networkManager.throwIfNoConnection()
        val articles = articleRemoteGateway.loadArticles()
        // save articles
        Timber.d("articles: $articles")
    }
}
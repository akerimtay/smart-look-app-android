package com.akerimtay.smartwardrobe.articles.domain

import com.akerimtay.smartwardrobe.articles.domain.gateway.ArticleLocalGateway
import com.akerimtay.smartwardrobe.articles.domain.gateway.ArticleRemoteGateway
import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.network.NetworkManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class LoadArticlesUseCase(
    private val networkManager: NetworkManager,
    private val articleRemoteGateway: ArticleRemoteGateway,
    private val articleLocalGateway: ArticleLocalGateway,
) : UseCase<Unit, Unit>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Unit) {
        networkManager.throwIfNoConnection()
        articleRemoteGateway.loadArticles().also { articleLocalGateway.save(it) }
    }
}
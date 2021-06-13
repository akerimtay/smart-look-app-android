package com.akerimtay.smartwardrobe.articles.domain

import com.akerimtay.smartwardrobe.articles.domain.gateway.ArticleLocalGateway
import com.akerimtay.smartwardrobe.articles.model.Article
import com.akerimtay.smartwardrobe.common.base.UseCaseSync
import kotlinx.coroutines.flow.Flow

class GetArticlesLiveDataUseCase(
    private val articleLocalGateway: ArticleLocalGateway,
) : UseCaseSync<Unit, Flow<List<Article>>>() {
    override fun execute(parameters: Unit): Flow<List<Article>> = articleLocalGateway.getAllAsFlow()
}
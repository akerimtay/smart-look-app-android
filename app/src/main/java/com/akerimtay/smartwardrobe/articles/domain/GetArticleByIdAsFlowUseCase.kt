package com.akerimtay.smartwardrobe.articles.domain

import com.akerimtay.smartwardrobe.articles.domain.GetArticleByIdAsFlowUseCase.Param
import com.akerimtay.smartwardrobe.articles.domain.gateway.ArticleLocalGateway
import com.akerimtay.smartwardrobe.articles.model.Article
import com.akerimtay.smartwardrobe.common.base.UseCaseSync
import kotlinx.coroutines.flow.Flow

class GetArticleByIdAsFlowUseCase(
    private val articleLocalGateway: ArticleLocalGateway
) : UseCaseSync<Param, Flow<Article?>>() {
    override fun execute(parameters: Param): Flow<Article?> = articleLocalGateway.getByIdAsFlow(parameters.articleId)

    data class Param(val articleId: Long)
}
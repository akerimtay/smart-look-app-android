package com.akerimtay.smartwardrobe.articles.domain

import androidx.lifecycle.LiveData
import com.akerimtay.smartwardrobe.articles.domain.gateway.ArticleLocalGateway
import com.akerimtay.smartwardrobe.articles.model.Article
import com.akerimtay.smartwardrobe.common.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetArticlesLiveDataUseCase(
    private val articleLocalGateway: ArticleLocalGateway,
) : UseCase<Unit, LiveData<List<Article>>>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Unit): LiveData<List<Article>> = articleLocalGateway.getAllAsLiveData()
}
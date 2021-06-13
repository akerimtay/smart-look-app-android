package com.akerimtay.smartwardrobe.articles.domain.gateway

import com.akerimtay.smartwardrobe.articles.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleLocalGateway {
    fun getAllAsLiveData(): Flow<List<Article>>
    suspend fun save(articles: List<Article>)
    suspend fun deleteAll()
}
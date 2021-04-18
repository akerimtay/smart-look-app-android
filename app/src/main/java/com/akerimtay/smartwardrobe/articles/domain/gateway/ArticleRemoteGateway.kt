package com.akerimtay.smartwardrobe.articles.domain.gateway

import com.akerimtay.smartwardrobe.articles.model.Article

interface ArticleRemoteGateway {
    suspend fun loadArticles(): List<Article>
}
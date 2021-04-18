package com.akerimtay.smartwardrobe.articles.domain

import com.akerimtay.smartwardrobe.articles.model.Article

interface ArticleRemoteGateway {
    suspend fun loadArticles(): List<Article>
}
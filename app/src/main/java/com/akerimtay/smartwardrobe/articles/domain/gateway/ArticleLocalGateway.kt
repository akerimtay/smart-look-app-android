package com.akerimtay.smartwardrobe.articles.domain.gateway

import androidx.lifecycle.LiveData
import com.akerimtay.smartwardrobe.articles.model.Article

interface ArticleLocalGateway {
    suspend fun getAllAsLiveData(): LiveData<List<Article>>
    suspend fun save(articles: List<Article>)
    suspend fun deleteAll()
}
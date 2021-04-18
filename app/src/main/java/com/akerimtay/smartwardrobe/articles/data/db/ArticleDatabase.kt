package com.akerimtay.smartwardrobe.articles.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.akerimtay.smartwardrobe.articles.data.ArticleConverter
import com.akerimtay.smartwardrobe.articles.domain.gateway.ArticleLocalGateway
import com.akerimtay.smartwardrobe.articles.model.Article

class ArticleDatabase(
    private val articleDao: ArticleDao,
) : ArticleLocalGateway {

    override suspend fun getAllAsLiveData(): LiveData<List<Article>> =
        articleDao.getAllAsLiveData().map { ArticleConverter.fromDatabase(it) }

    override suspend fun save(articles: List<Article>) {
        articleDao.save(ArticleConverter.toDatabase(articles))
    }

    override suspend fun deleteAll() {
        articleDao.deleteAll()
    }
}
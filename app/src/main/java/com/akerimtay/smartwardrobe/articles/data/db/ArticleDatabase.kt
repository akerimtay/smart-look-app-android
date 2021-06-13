package com.akerimtay.smartwardrobe.articles.data.db

import com.akerimtay.smartwardrobe.articles.data.ArticleConverter
import com.akerimtay.smartwardrobe.articles.domain.gateway.ArticleLocalGateway
import com.akerimtay.smartwardrobe.articles.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArticleDatabase(
    private val articleDao: ArticleDao,
) : ArticleLocalGateway {

    override fun getAllAsLiveData(): Flow<List<Article>> =
        articleDao.getAllAsLiveData().map { ArticleConverter.fromDatabase(it) }

    override suspend fun save(articles: List<Article>) {
        articleDao.save(ArticleConverter.toDatabase(articles))
    }

    override suspend fun deleteAll() {
        articleDao.deleteAll()
    }
}
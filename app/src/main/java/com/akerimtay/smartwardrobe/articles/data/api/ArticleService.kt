package com.akerimtay.smartwardrobe.articles.data.api

import com.akerimtay.smartwardrobe.articles.data.ArticleConverter
import com.akerimtay.smartwardrobe.articles.domain.gateway.ArticleRemoteGateway
import com.akerimtay.smartwardrobe.articles.model.Article
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

private const val ARTICLES = "articles"

class ArticleService(
    private val database: FirebaseFirestore,
) : ArticleRemoteGateway {
    override suspend fun loadArticles(): List<Article> {
        val response = database.collection(ARTICLES)
            .get()
            .await()
            .toObjects(ArticleResponse::class.java)
        return ArticleConverter.fromNetwork(response)
    }
}
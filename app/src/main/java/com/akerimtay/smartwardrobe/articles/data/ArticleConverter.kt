package com.akerimtay.smartwardrobe.articles.data

import com.akerimtay.smartwardrobe.articles.model.Article
import com.akerimtay.smartwardrobe.user.model.Gender

object ArticleConverter {
    fun fromNetwork(response: ArticleResponse): Article =
        Article(
            id = response.id ?: 0,
            name = response.name.orEmpty(),
            description = response.description.orEmpty(),
            gender = Gender.toGender(response.gender),
            imageUrl = response.imageUrl.orEmpty(),
            sourceUrl = response.sourceUrl.orEmpty()
        )

    fun fromNetwork(responses: List<ArticleResponse>): List<Article> = responses.map { fromNetwork(it) }
}
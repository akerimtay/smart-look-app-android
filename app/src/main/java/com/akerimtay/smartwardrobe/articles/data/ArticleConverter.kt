package com.akerimtay.smartwardrobe.articles.data

import com.akerimtay.smartwardrobe.articles.data.api.ArticleResponse
import com.akerimtay.smartwardrobe.articles.data.db.ArticleEntity
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

    fun toDatabase(model: Article): ArticleEntity =
        ArticleEntity(
            id = model.id,
            name = model.name,
            description = model.description,
            gender = model.gender,
            imageUrl = model.imageUrl,
            sourceUrl = model.sourceUrl
        )

    fun toDatabase(models: List<Article>): List<ArticleEntity> = models.map { toDatabase(it) }

    fun fromDatabase(entity: ArticleEntity): Article = Article(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        gender = entity.gender,
        imageUrl = entity.imageUrl,
        sourceUrl = entity.sourceUrl
    )

    fun fromDatabase(entities: List<ArticleEntity>): List<Article> = entities.map { fromDatabase(it) }
}
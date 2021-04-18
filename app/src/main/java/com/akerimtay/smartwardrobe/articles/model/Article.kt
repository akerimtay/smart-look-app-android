package com.akerimtay.smartwardrobe.articles.model

import com.akerimtay.smartwardrobe.user.model.Gender

data class Article(
    val id: Long,
    val name: String,
    val description: String,
    val gender: Gender,
    val imageUrl: String,
    val sourceUrl: String,
)
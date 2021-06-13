package com.akerimtay.smartwardrobe.favorites.model

data class Favorite(
    val id: Long,
    val userId: String,
    val outfitId: Long
)
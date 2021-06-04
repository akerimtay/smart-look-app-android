package com.akerimtay.smartwardrobe.favorites.model

import com.akerimtay.smartwardrobe.outfit.model.Outfit

data class FavoriteDetail(
    val favorite: Favorite,
    val outfit: Outfit?
)
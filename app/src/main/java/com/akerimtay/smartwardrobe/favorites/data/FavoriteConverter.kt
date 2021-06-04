package com.akerimtay.smartwardrobe.favorites.data

import com.akerimtay.smartwardrobe.favorites.data.db.FavoriteDetailEntity
import com.akerimtay.smartwardrobe.favorites.data.db.FavoriteEntity
import com.akerimtay.smartwardrobe.favorites.model.Favorite
import com.akerimtay.smartwardrobe.favorites.model.FavoriteDetail
import com.akerimtay.smartwardrobe.outfit.data.OutfitConverter

object FavoriteConverter {
    fun toDatabase(model: Favorite): FavoriteEntity =
        FavoriteEntity(
            id = model.id,
            userId = model.userId,
            outfitId = model.outfitId
        )

    fun fromDatabase(entity: FavoriteEntity): Favorite =
        Favorite(
            id = entity.id,
            userId = entity.userId,
            outfitId = entity.outfitId
        )

    fun fromDatabase(entity: FavoriteDetailEntity): FavoriteDetail =
        FavoriteDetail(
            favorite = fromDatabase(entity.favoriteEntity),
            outfit = entity.outfitEntity?.let { OutfitConverter.fromDatabase(it) }
        )
}
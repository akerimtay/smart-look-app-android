package com.akerimtay.smartwardrobe.favorites.data.db

import androidx.room.Embedded
import androidx.room.Relation
import com.akerimtay.smartwardrobe.outfit.data.db.OutfitEntity

class FavoriteDetailEntity {
    @Embedded
    lateinit var favoriteEntity: FavoriteEntity

    @Relation(
        parentColumn = FavoriteEntity.OUTFIT_ID,
        entityColumn = OutfitEntity.ID,
        entity = OutfitEntity::class
    )
    var outfitEntity: OutfitEntity? = null
}
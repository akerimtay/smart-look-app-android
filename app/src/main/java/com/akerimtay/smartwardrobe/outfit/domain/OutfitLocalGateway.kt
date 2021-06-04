package com.akerimtay.smartwardrobe.outfit.domain

import androidx.paging.DataSource
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import com.akerimtay.smartwardrobe.outfit.model.Season
import kotlinx.coroutines.flow.Flow

interface OutfitLocalGateway {
    suspend fun save(outfits: List<Outfit>)
    fun getByGender(gender: OutfitGender): DataSource.Factory<Int, Outfit>
    suspend fun deleteAll()
    fun getByIdAsFlow(id: Long): Flow<Outfit?>
    fun getSimilarAsFlow(
        id: Long,
        season: Season,
        gender: OutfitGender,
        limit: Long
    ): Flow<List<Outfit>>
}
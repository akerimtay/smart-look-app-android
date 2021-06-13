package com.akerimtay.smartwardrobe.favorites.domain

import com.akerimtay.smartwardrobe.favorites.model.Favorite
import com.akerimtay.smartwardrobe.favorites.model.FavoriteDetail
import kotlinx.coroutines.flow.Flow

interface FavoriteLocalGateway {
    suspend fun save(favorite: Favorite)
    suspend fun deleteById(id: Long)
    suspend fun deleteByOutfitId(userId: String, outfitId: Long)
    fun getAllByUserIdAsFlow(userId: String): Flow<List<FavoriteDetail>>
    fun getCountAsFlow(userId: String, outfitId: Long): Flow<Long>
}
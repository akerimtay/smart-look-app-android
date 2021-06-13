package com.akerimtay.smartwardrobe.favorites.data.db

import com.akerimtay.smartwardrobe.favorites.data.FavoriteConverter
import com.akerimtay.smartwardrobe.favorites.domain.FavoriteLocalGateway
import com.akerimtay.smartwardrobe.favorites.model.Favorite
import com.akerimtay.smartwardrobe.favorites.model.FavoriteDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteDatabase(private val favoriteDao: FavoriteDao) : FavoriteLocalGateway {
    override suspend fun save(favorite: Favorite) {
        favoriteDao.save(FavoriteConverter.toDatabase(favorite))
    }

    override suspend fun deleteById(id: Long) {
        favoriteDao.deleteById(id)
    }

    override suspend fun deleteByOutfitId(userId: String, outfitId: Long) {
        favoriteDao.deleteByOutfitId(userId = userId, outfitId = outfitId)
    }

    override fun getAllByUserIdAsFlow(userId: String): Flow<List<FavoriteDetail>> =
        favoriteDao.getAllByUserIdAsFlow(userId).map { list -> list.map { FavoriteConverter.fromDatabase(it) } }

    override fun getCountAsFlow(userId: String, outfitId: Long): Flow<Long> =
        favoriteDao.getCountAsFlow(userId = userId, outfitId = outfitId)
}
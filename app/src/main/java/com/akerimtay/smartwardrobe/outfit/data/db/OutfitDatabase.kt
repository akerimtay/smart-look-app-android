package com.akerimtay.smartwardrobe.outfit.data.db

import androidx.paging.DataSource
import androidx.room.withTransaction
import com.akerimtay.smartwardrobe.database.AppDatabase
import com.akerimtay.smartwardrobe.outfit.data.OutfitConverter
import com.akerimtay.smartwardrobe.outfit.domain.OutfitLocalGateway
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import com.akerimtay.smartwardrobe.outfit.model.Season
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OutfitDatabase(
    private val database: AppDatabase,
    private val outfitDao: OutfitDao
) : OutfitLocalGateway {
    override suspend fun save(outfits: List<Outfit>) {
        database.withTransaction {
            outfitDao.save(OutfitConverter.toDatabase(outfits))
        }
    }

    override fun getByGender(gender: OutfitGender): DataSource.Factory<Int, Outfit> =
        outfitDao.getByGender(gender = gender).map { OutfitConverter.fromDatabase(it) }

    override suspend fun deleteAll() {
        outfitDao.deleteAll()
    }

    override fun getByIdAsFlow(id: Long): Flow<Outfit?> =
        outfitDao.getByIdAsFlow(id).map { entity -> entity?.let { OutfitConverter.fromDatabase(it) } }

    override fun getSimilarAsFlow(id: Long, season: Season, gender: OutfitGender, limit: Long): Flow<List<Outfit>> =
        outfitDao.getSimilarAsFlow(
            id = id,
            season = season,
            gender = gender,
            limit = limit
        ).map { OutfitConverter.fromDatabase(it) }
}
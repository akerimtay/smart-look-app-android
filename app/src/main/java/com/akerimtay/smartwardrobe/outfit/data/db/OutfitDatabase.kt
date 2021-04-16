package com.akerimtay.smartwardrobe.outfit.data.db

import androidx.paging.DataSource
import com.akerimtay.smartwardrobe.outfit.data.OutfitConverter
import com.akerimtay.smartwardrobe.outfit.domain.OutfitLocalGateway
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender

class OutfitDatabase(private val outfitDao: OutfitDao) : OutfitLocalGateway {
    override fun save(outfits: List<Outfit>) {
        outfitDao.save(OutfitConverter.toDatabase(outfits))
    }

    override fun getByGender(gender: OutfitGender): DataSource.Factory<Int, Outfit> =
        outfitDao.getByGender(gender = gender).map { OutfitConverter.fromDatabase(it) }

    override fun deleteAll() {
        outfitDao.deleteAll()
    }
}
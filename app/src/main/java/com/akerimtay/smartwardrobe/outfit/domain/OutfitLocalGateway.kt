package com.akerimtay.smartwardrobe.outfit.domain

import androidx.paging.DataSource
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender

interface OutfitLocalGateway {
    fun save(outfits: List<Outfit>)
    fun getByGender(gender: OutfitGender): DataSource.Factory<Int, Outfit>
    fun deleteAll()
}
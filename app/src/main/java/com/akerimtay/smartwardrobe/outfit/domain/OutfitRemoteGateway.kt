package com.akerimtay.smartwardrobe.outfit.domain

import com.akerimtay.smartwardrobe.outfit.model.Outfit
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender

interface OutfitRemoteGateway {
    suspend fun loadOutlets(
        gender: OutfitGender,
        after: String? = null,
        limit: Long
    ): List<Outfit>
}
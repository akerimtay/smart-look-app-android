package com.akerimtay.smartwardrobe.outfit.domain

import com.akerimtay.smartwardrobe.common.base.UseCaseSync
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import com.akerimtay.smartwardrobe.outfit.model.Season
import kotlinx.coroutines.flow.Flow

class GetSimilarOutfitsUseCase(
    private val outfitLocalGateway: OutfitLocalGateway
) : UseCaseSync<GetSimilarOutfitsUseCase.Param, Flow<List<Outfit>>>() {
    override fun execute(parameters: Param): Flow<List<Outfit>> = outfitLocalGateway.getSimilarAsFlow(
        id = parameters.id,
        season = parameters.season,
        gender = parameters.gender,
        limit = parameters.limit
    )

    data class Param(
        val id: Long,
        val season: Season,
        val gender: OutfitGender,
        val limit: Long
    )
}
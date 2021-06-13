package com.akerimtay.smartwardrobe.outfit.domain

import com.akerimtay.smartwardrobe.common.base.UseCaseSync
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import kotlinx.coroutines.flow.Flow

class GetOutfitByIdAsFlowUseCase(
    private val outfitLocalGateway: OutfitLocalGateway
) : UseCaseSync<GetOutfitByIdAsFlowUseCase.Param, Flow<Outfit?>>() {
    override fun execute(parameters: Param): Flow<Outfit?> =
        outfitLocalGateway.getByIdAsFlow(parameters.outfitId)

    data class Param(val outfitId: Long)
}
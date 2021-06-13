package com.akerimtay.smartwardrobe.outfit.domain

import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class GetOutfitByIdAsFlowUseCase(
    private val outfitLocalGateway: OutfitLocalGateway
) : UseCase<GetOutfitByIdAsFlowUseCase.Param, Flow<Outfit?>>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Param): Flow<Outfit?> =
        outfitLocalGateway.getByIdAsFlow(parameters.outfitId)

    data class Param(val outfitId: Long)
}
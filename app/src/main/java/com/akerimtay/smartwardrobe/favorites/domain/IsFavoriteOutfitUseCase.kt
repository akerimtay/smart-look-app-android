package com.akerimtay.smartwardrobe.favorites.domain

import com.akerimtay.smartwardrobe.common.base.UseCaseSync
import com.akerimtay.smartwardrobe.common.persistence.PreferencesContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class IsFavoriteOutfitUseCase(
    private val preferences: PreferencesContract,
    private val favoriteLocalGateway: FavoriteLocalGateway
) : UseCaseSync<IsFavoriteOutfitUseCase.Param, Flow<Boolean>>() {

    override fun execute(parameters: Param): Flow<Boolean> = preferences.userId?.let {
        favoriteLocalGateway.getCountAsFlow(
            userId = it,
            outfitId = parameters.outfitId
        ).map { count -> count > 0 }
    } ?: flowOf(false)

    data class Param(val outfitId: Long)
}
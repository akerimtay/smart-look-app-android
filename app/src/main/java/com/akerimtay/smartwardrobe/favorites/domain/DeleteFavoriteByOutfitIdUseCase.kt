package com.akerimtay.smartwardrobe.favorites.domain

import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.common.persistence.PreferencesContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DeleteFavoriteByOutfitIdUseCase(
    private val preferences: PreferencesContract,
    private val favoriteLocalGateway: FavoriteLocalGateway
) : UseCase<DeleteFavoriteByOutfitIdUseCase.Param, Unit>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Param) {
        preferences.userId?.let {
            favoriteLocalGateway.deleteByOutfitId(
                userId = it,
                outfitId = parameters.outfitId
            )
        }
    }

    data class Param(val outfitId: Long)
}
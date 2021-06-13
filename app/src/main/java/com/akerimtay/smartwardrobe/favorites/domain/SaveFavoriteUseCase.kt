package com.akerimtay.smartwardrobe.favorites.domain

import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.favorites.model.Favorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SaveFavoriteUseCase(
    private val favoriteLocalGateway: FavoriteLocalGateway
) : UseCase<SaveFavoriteUseCase.Param, Unit>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Param) {
        favoriteLocalGateway.save(parameters.favorite)
    }

    data class Param(val favorite: Favorite)
}
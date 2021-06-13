package com.akerimtay.smartwardrobe.favorites.domain

import com.akerimtay.smartwardrobe.common.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DeleteFavoriteByIdUseCase(
    private val favoriteLocalGateway: FavoriteLocalGateway
) : UseCase<DeleteFavoriteByIdUseCase.Param, Unit>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Param) {
        favoriteLocalGateway.deleteById(parameters.favoriteId)
    }

    data class Param(val favoriteId: Long)
}
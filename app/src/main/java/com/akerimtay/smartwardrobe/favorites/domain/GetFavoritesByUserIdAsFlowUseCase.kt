package com.akerimtay.smartwardrobe.favorites.domain

import com.akerimtay.smartwardrobe.common.base.UseCaseSync
import com.akerimtay.smartwardrobe.favorites.model.FavoriteDetail
import kotlinx.coroutines.flow.Flow

class GetFavoritesByUserIdAsFlowUseCase(
    private val favoriteLocalGateway: FavoriteLocalGateway
) : UseCaseSync<GetFavoritesByUserIdAsFlowUseCase.Param, Flow<List<FavoriteDetail>>>() {

    override fun execute(parameters: Param): Flow<List<FavoriteDetail>> =
        favoriteLocalGateway.getAllByUserIdAsFlow(parameters.userId)

    data class Param(val userId: String)
}
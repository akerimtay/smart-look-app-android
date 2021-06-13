package com.akerimtay.smartwardrobe.outfit.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.akerimtay.smartwardrobe.common.base.UseCaseSync
import com.akerimtay.smartwardrobe.outfit.data.OutfitRemoteMediator
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import kotlinx.coroutines.flow.Flow

class GetOutfitsUseCaseAsFlow(
    private val outfitRemoteGateway: OutfitRemoteGateway,
    private val outfitLocalGateway: OutfitLocalGateway,
) : UseCaseSync<GetOutfitsUseCaseAsFlow.Param, Flow<PagingData<Outfit>>>() {

    override fun execute(parameters: Param): Flow<PagingData<Outfit>> = Pager(
        config = PagingConfig(pageSize = parameters.pageSize),
        remoteMediator = OutfitRemoteMediator(
            gender = parameters.gender,
            outfitRemoteGateway = outfitRemoteGateway,
            outfitLocalGateway = outfitLocalGateway
        ),
        pagingSourceFactory = outfitLocalGateway.getByGender(gender = parameters.gender).asPagingSourceFactory()
    ).flow

    data class Param(
        val gender: OutfitGender,
        val pageSize: Int,
    )
}
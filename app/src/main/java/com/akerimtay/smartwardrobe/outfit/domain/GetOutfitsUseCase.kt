package com.akerimtay.smartwardrobe.outfit.domain

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.outfit.data.OutfitRemoteMediator
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetOutfitsUseCase(
    private val outfitRemoteGateway: OutfitRemoteGateway,
    private val outfitLocalGateway: OutfitLocalGateway,
) : UseCase<GetOutfitsUseCase.Param, LiveData<PagingData<Outfit>>>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Param): LiveData<PagingData<Outfit>> = Pager(
        config = PagingConfig(pageSize = parameters.pageSize),
        remoteMediator = OutfitRemoteMediator(
            gender = parameters.gender,
            outfitRemoteGateway = outfitRemoteGateway,
            outfitLocalGateway = outfitLocalGateway
        ),
        pagingSourceFactory = outfitLocalGateway.getByGender(gender = parameters.gender).asPagingSourceFactory()
    ).liveData

    data class Param(
        val gender: OutfitGender,
        val pageSize: Int,
    )
}
package com.akerimtay.smartwardrobe.outfit.data

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.akerimtay.smartwardrobe.outfit.domain.OutfitLocalGateway
import com.akerimtay.smartwardrobe.outfit.domain.OutfitRemoteGateway
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import timber.log.Timber

class OutfitRemoteMediator(
    private val gender: OutfitGender,
    private val outfitRemoteGateway: OutfitRemoteGateway,
    private val outfitLocalGateway: OutfitLocalGateway,
) : RemoteMediator<Int, Outfit>() {
    override suspend fun initialize(): InitializeAction = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Outfit>): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem =
                        state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastItem.id
                }
            }

            val data = outfitRemoteGateway.loadOutlets(
                gender = gender,
                after = loadKey,
                limit = state.config.pageSize.toLong()
            ).also { outfitLocalGateway.save(it) }

            MediatorResult.Success(endOfPaginationReached = data.isEmpty())
        } catch (e: Exception) {
            Timber.e(e, "Can't load outfits")
            MediatorResult.Error(e)
        }
    }
}
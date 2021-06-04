package com.akerimtay.smartwardrobe.favorites.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import com.akerimtay.smartwardrobe.common.base.adapter.BaseContentItem
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.content.item.OutfitItem
import com.akerimtay.smartwardrobe.favorites.domain.GetFavoritesByUserIdAsFlowUseCase
import com.akerimtay.smartwardrobe.user.domain.GetCurrentUserAsFlowUseCase
import kotlinx.coroutines.flow.map

class FavoritesViewModel(
    getFavoritesByUserIdAsFlowUseCase: GetFavoritesByUserIdAsFlowUseCase,
    private val getCurrentUserAsFlowUseCase: GetCurrentUserAsFlowUseCase,
) : BaseViewModel() {
    private val _actions = SingleLiveEvent<FavoritesAction>()
    val actions: LiveData<FavoritesAction> = _actions

    private val currentUser = liveData { emitSource(getCurrentUserAsFlowUseCase(Unit)) }
    val favorites = currentUser.switchMap { user ->
        liveData {
            user?.let { user ->
                getFavoritesByUserIdAsFlowUseCase(GetFavoritesByUserIdAsFlowUseCase.Param(userId = user.id)).map { list ->
                    list.map { favoriteDetail ->
                        favoriteDetail.outfit?.let { outfit ->
                            OutfitItem(
                                outfit = outfit,
                                onItemClickListener = {
                                    _actions.postValue(FavoritesAction.ShowOutfitDetailScreen(outfit.id))
                                }
                            )
                        } as BaseContentItem<ItemContentType>
                    }
                }.asLiveData()
            }?.let { emitSource(it) }
        }
    }
}

sealed class FavoritesAction : Action {
    data class ShowOutfitDetailScreen(val outfitId: Long) : FavoritesAction()
}
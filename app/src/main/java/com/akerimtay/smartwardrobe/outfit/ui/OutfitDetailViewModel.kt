package com.akerimtay.smartwardrobe.outfit.ui

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseError
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import com.akerimtay.smartwardrobe.common.base.adapter.BaseContentItem
import com.akerimtay.smartwardrobe.common.persistence.PreferencesContract
import com.akerimtay.smartwardrobe.common.utils.RandomHelper
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.content.item.OutfitDetailItem
import com.akerimtay.smartwardrobe.favorites.domain.DeleteFavoriteByOutfitIdUseCase
import com.akerimtay.smartwardrobe.favorites.domain.IsFavoriteOutfitUseCase
import com.akerimtay.smartwardrobe.favorites.domain.SaveFavoriteUseCase
import com.akerimtay.smartwardrobe.favorites.model.Favorite
import com.akerimtay.smartwardrobe.outfit.domain.GetOutfitByIdAsFlowUseCase
import com.akerimtay.smartwardrobe.outfit.domain.GetSimilarOutfitsUseCase
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import kotlinx.coroutines.flow.map
import timber.log.Timber

private const val LIMIT = 10L

class OutfitDetailViewModel(
    private val outfitId: Long,
    private val preferences: PreferencesContract,
    private val getOutfitByIdAsFlowUseCase: GetOutfitByIdAsFlowUseCase,
    private val getSimilarOutfitsUseCase: GetSimilarOutfitsUseCase,
    isFavoriteOutfitUseCase: IsFavoriteOutfitUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val deleteFavoriteByOutfitIdUseCase: DeleteFavoriteByOutfitIdUseCase
) : BaseViewModel() {
    private val _actions = SingleLiveEvent<OutfitDetailAction>()
    val actions: LiveData<OutfitDetailAction> = _actions

    private val _favoriteProgressLoading = MutableLiveData<Boolean>(false)
    val favoriteProgressLoading: LiveData<Boolean> = _favoriteProgressLoading

    val outfit: LiveData<Outfit?> = liveData {
        emitSource(getOutfitByIdAsFlowUseCase(GetOutfitByIdAsFlowUseCase.Param(outfitId = outfitId)).asLiveData())
    }
    val similarOutfits = outfit.switchMap { data ->
        liveData {
            data?.let { data ->
                getSimilarOutfitsUseCase(
                    GetSimilarOutfitsUseCase.Param(
                        id = outfitId,
                        season = data.season,
                        gender = data.gender,
                        limit = LIMIT
                    )
                ).map { list ->
                    list.map {
                        OutfitDetailItem(
                            outfit = it,
                            onItemClickListener = {
                                _actions.postValue(OutfitDetailAction.ShowSimilarOutfit(outfitId = it.id))
                            }
                        ) as BaseContentItem<ItemContentType>
                    }
                }.asLiveData()
            }?.let { emitSource(it) }
        }
    }
    val isFavoriteOutfit: LiveData<Boolean> =
        isFavoriteOutfitUseCase(IsFavoriteOutfitUseCase.Param(outfitId = outfitId)).asLiveData()

    fun addFavorite() {
        launchSafe(
            start = { _favoriteProgressLoading.value = true },
            finish = { _favoriteProgressLoading.value = false },
            body = {
                val favorite = Favorite(
                    id = RandomHelper.generateRandomNumbers(),
                    userId = preferences.userId ?: throw BaseError.UserNotFound,
                    outfitId = outfitId
                )
                saveFavoriteUseCase(SaveFavoriteUseCase.Param(favorite = favorite))
            },
            handleError = {
                Timber.e(it, "Couldn't save favorite")
                _actions.postValue(
                    when (it) {
                        is BaseError -> OutfitDetailAction.ShowMessage(messageResId = it.errorResId)
                        else -> OutfitDetailAction.ShowMessage(messageResId = R.string.error_favorite_save)
                    }
                )
            }
        )
    }

    fun deleteFavorite() {
        launchSafe(
            start = { _favoriteProgressLoading.value = true },
            finish = { _favoriteProgressLoading.value = false },
            body = {
                deleteFavoriteByOutfitIdUseCase(DeleteFavoriteByOutfitIdUseCase.Param(outfitId))
            },
            handleError = {
                Timber.e(it, "Couldn't delete favorite")
                _actions.postValue(OutfitDetailAction.ShowMessage(messageResId = R.string.error_favorite_delete))
            }
        )
    }
}

sealed class OutfitDetailAction : Action {
    data class ShowMessage(@StringRes val messageResId: Int) : OutfitDetailAction()
    data class ShowSimilarOutfit(val outfitId: Long) : OutfitDetailAction()
}
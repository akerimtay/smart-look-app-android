package com.akerimtay.smartwardrobe.outfit.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseError
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import com.akerimtay.smartwardrobe.common.base.adapter.BaseContentItem
import com.akerimtay.smartwardrobe.common.model.ErrorMessage
import com.akerimtay.smartwardrobe.common.persistence.PreferencesContract
import com.akerimtay.smartwardrobe.common.utils.RandomHelper
import com.akerimtay.smartwardrobe.common.utils.getErrorMessage
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.content.item.OutfitDetailItem
import com.akerimtay.smartwardrobe.favorites.domain.DeleteFavoriteByOutfitIdUseCase
import com.akerimtay.smartwardrobe.favorites.domain.IsFavoriteOutfitUseCase
import com.akerimtay.smartwardrobe.favorites.domain.SaveFavoriteUseCase
import com.akerimtay.smartwardrobe.favorites.model.Favorite
import com.akerimtay.smartwardrobe.outfit.domain.GetOutfitByIdAsFlowUseCase
import com.akerimtay.smartwardrobe.outfit.domain.GetSimilarOutfitsUseCase
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender.MALE
import com.akerimtay.smartwardrobe.outfit.model.Season.SUMMER
import kotlinx.coroutines.flow.map
import timber.log.Timber

private const val LIMIT = 10L

class OutfitDetailViewModel(
    private val outfitId: Long,
    private val preferences: PreferencesContract,
    getOutfitByIdAsFlowUseCase: GetOutfitByIdAsFlowUseCase,
    private val getSimilarOutfitsUseCase: GetSimilarOutfitsUseCase,
    isFavoriteOutfitUseCase: IsFavoriteOutfitUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val deleteFavoriteByOutfitIdUseCase: DeleteFavoriteByOutfitIdUseCase
) : BaseViewModel() {
    private val _actions = SingleLiveEvent<OutfitDetailAction>()
    val actions: LiveData<OutfitDetailAction> = _actions

    private val _favoriteProgressLoading = MutableLiveData(false)
    val favoriteProgressLoading: LiveData<Boolean> = _favoriteProgressLoading

    val outfit: LiveData<Outfit?> =
        getOutfitByIdAsFlowUseCase(GetOutfitByIdAsFlowUseCase.Param(outfitId = outfitId)).asLiveData()
    val similarOutfits: LiveData<List<BaseContentItem<ItemContentType>>> = outfit.switchMap { data ->
        getSimilarOutfitsUseCase(
            GetSimilarOutfitsUseCase.Param(
                id = outfitId,
                season = data?.season ?: SUMMER,
                gender = data?.gender ?: MALE,
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
                    OutfitDetailAction.ShowErrorMessage(it.getErrorMessage(R.string.error_favorite_save))
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
                _actions.postValue(
                    OutfitDetailAction.ShowErrorMessage(it.getErrorMessage(R.string.error_favorite_delete))
                )
            }
        )
    }
}

sealed class OutfitDetailAction : Action {
    data class ShowErrorMessage(val errorMessage: ErrorMessage) : OutfitDetailAction()
    data class ShowSimilarOutfit(val outfitId: Long) : OutfitDetailAction()
}
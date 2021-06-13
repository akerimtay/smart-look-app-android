package com.akerimtay.smartwardrobe.feed.ui

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.akerimtay.smartwardrobe.R.string
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseError
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import com.akerimtay.smartwardrobe.common.base.adapter.BaseContentItem
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.content.item.OutfitItem
import com.akerimtay.smartwardrobe.feed.ui.FeedAction.ShowMessage
import com.akerimtay.smartwardrobe.outfit.domain.GetOutfitsUseCaseAsFlow
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import com.akerimtay.smartwardrobe.user.domain.GetCurrentUserAsFlowUseCase
import com.akerimtay.smartwardrobe.user.model.Gender
import com.akerimtay.smartwardrobe.weather.domain.GetCurrentWeatherUseCase
import com.akerimtay.smartwardrobe.weather.domain.LoadWeatherUseCase
import com.akerimtay.smartwardrobe.weather.model.Weather
import timber.log.Timber

private const val UNIT = "metric"
private const val LANGUAGE = "ru"
private const val PAGE_SIZE = 20

class FeedViewModel(
    private val getOutfitsUseCaseAsFlow: GetOutfitsUseCaseAsFlow,
    private val loadWeatherUseCase: LoadWeatherUseCase,
    getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    getCurrentUserAsFlowUseCase: GetCurrentUserAsFlowUseCase,
) : BaseViewModel() {
    private val _actions = SingleLiveEvent<FeedAction>()
    val actions: LiveData<FeedAction> = _actions

    private val _progressLoading = MutableLiveData(false)
    val progressLoading: LiveData<Boolean> = _progressLoading

    val weather: LiveData<Weather?> = getCurrentWeatherUseCase(Unit).asLiveData()

    private val currentUser = getCurrentUserAsFlowUseCase(Unit).asLiveData()

    val outfits = currentUser.switchMap { user ->
        liveData<PagingData<BaseContentItem<ItemContentType>>> {
            emitSource(
                getOutfitsUseCaseAsFlow(
                    GetOutfitsUseCaseAsFlow.Param(
                        gender = when (user?.gender) {
                            Gender.MALE -> OutfitGender.MALE
                            Gender.FEMALE -> OutfitGender.FEMALE
                            else -> OutfitGender.MALE
                        },
                        pageSize = PAGE_SIZE
                    )
                ).cachedIn(viewModelScope)
                    .asLiveData()
                    .map { pagingData ->
                        pagingData.map { outfit ->
                            OutfitItem(
                                outfit = outfit,
                                onItemClickListener = {
                                    _actions.postValue(FeedAction.ShowOutfitDetailScreen(outfitId = outfit.id))
                                }
                            )
                        }
                    }
            )
        }
    }

    fun loadWeather(longitude: Float, latitude: Float) {
        launchSafe(
            start = { _progressLoading.postValue(true) },
            finish = { _progressLoading.postValue(false) },
            body = {
                loadWeatherUseCase(
                    LoadWeatherUseCase.Param(
                        longitude = longitude,
                        latitude = latitude,
                        unit = UNIT,
                        language = LANGUAGE
                    )
                )
            },
            handleError = {
                Timber.e(it, "Can't load weather")
                _actions.postValue(
                    when (it) {
                        is BaseError -> ShowMessage(messageResId = it.errorResId)
                        else -> ShowMessage(messageResId = string.error_load_weather)
                    }
                )
            }
        )
    }
}

sealed class FeedAction : Action {
    data class ShowMessage(@StringRes val messageResId: Int) : FeedAction()
    data class ShowOutfitDetailScreen(val outfitId: Long) : FeedAction()
}
package com.akerimtay.smartwardrobe.feed

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseError
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import com.akerimtay.smartwardrobe.weather.domain.GetCurrentWeatherUseCase
import com.akerimtay.smartwardrobe.weather.domain.LoadWeatherUseCase
import com.akerimtay.smartwardrobe.weather.model.Weather
import timber.log.Timber

private const val UNIT = "metric"
private const val LANGUAGE = "ru"

class FeedViewModel(
    private val loadWeatherUseCase: LoadWeatherUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
) : BaseViewModel() {

    private val _actions = SingleLiveEvent<FeedAction>()
    val actions: LiveData<FeedAction> = _actions

    private val _progressLoading = MutableLiveData(false)
    val progressLoading: LiveData<Boolean> = _progressLoading

    val weather: LiveData<Weather?> = liveData { emitSource(getCurrentWeatherUseCase(Unit)) }

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
                        is BaseError -> FeedAction.ShowMessage(messageResId = it.errorResId)
                        else -> FeedAction.ShowMessage(messageResId = R.string.error_load_weather)
                    }
                )
            }
        )
    }
}

sealed class FeedAction : Action {
    data class ShowMessage(@StringRes val messageResId: Int) : FeedAction()
}
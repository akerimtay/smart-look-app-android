package com.akerimtay.smartwardrobe.feed

import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.weather.domain.LoadWeatherUseCase
import timber.log.Timber

class FeedViewModel(
    private val loadWeatherUseCase: LoadWeatherUseCase
) : BaseViewModel() {

    fun loadWeather() {
        launchSafe(
            body = {
                loadWeatherUseCase(
                    LoadWeatherUseCase.Param(
                        latitude = 51.1,
                        longitude = 71.2,
                        language = "ru"
                    )
                )
            },
            handleError = {
                Timber.e(it, "Can't load weather")
            }
        )
    }

}
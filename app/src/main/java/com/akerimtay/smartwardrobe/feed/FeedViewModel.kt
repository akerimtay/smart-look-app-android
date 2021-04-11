package com.akerimtay.smartwardrobe.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
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

    val weather: LiveData<Weather?> = liveData { emitSource(getCurrentWeatherUseCase(Unit)) }

    fun loadWeather(longitude: Float, latitude: Float) {
        launchSafe(
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
            }
        )
    }

}
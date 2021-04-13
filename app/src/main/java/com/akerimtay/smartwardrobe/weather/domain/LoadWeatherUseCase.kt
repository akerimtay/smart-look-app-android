package com.akerimtay.smartwardrobe.weather.domain

import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.common.persistence.PreferencesContract
import com.akerimtay.smartwardrobe.network.NetworkManager
import com.akerimtay.smartwardrobe.weather.domain.gateway.WeatherLocalGateway
import com.akerimtay.smartwardrobe.weather.domain.gateway.WeatherRemoteGateway
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class LoadWeatherUseCase(
    private val networkManager: NetworkManager,
    private val preferencesContract: PreferencesContract,
    private val weatherRemoteGateway: WeatherRemoteGateway,
    private val weatherLocalGateway: WeatherLocalGateway,
) : UseCase<LoadWeatherUseCase.Param, Unit>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Param) {
        networkManager.throwIfNoConnection()
        weatherRemoteGateway.getWeather(
            latitude = parameters.latitude,
            longitude = parameters.longitude,
            unit = parameters.unit,
            language = parameters.language
        ).also { weather ->
            weatherLocalGateway.save(weather)
            preferencesContract.longitude = weather.longitude
            preferencesContract.latitude = weather.latitude
        }
    }

    data class Param(
        val latitude: Float,
        val longitude: Float,
        val unit: String,
        val language: String
    )
}
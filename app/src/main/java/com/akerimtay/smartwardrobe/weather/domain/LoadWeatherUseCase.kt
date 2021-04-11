package com.akerimtay.smartwardrobe.weather.domain

import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.network.NetworkManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class LoadWeatherUseCase(
    private val networkManager: NetworkManager,
    private val weatherRemoteGateway: WeatherRemoteGateway
) : UseCase<LoadWeatherUseCase.Param, Unit>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Param) {
        networkManager.throwIfNoConnection()
        val weather = weatherRemoteGateway.getWeather(
            latitude = parameters.latitude,
            longitude = parameters.longitude,
            language = parameters.language
        )
    }

    data class Param(val latitude: Double, val longitude: Double, val language: String)
}
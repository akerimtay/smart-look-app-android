package com.akerimtay.smartwardrobe.weather.data.api

import com.akerimtay.smartwardrobe.weather.data.WeatherConverter
import com.akerimtay.smartwardrobe.weather.domain.WeatherRemoteGateway
import com.akerimtay.smartwardrobe.weather.model.Weather

private const val UNIT = "METRIC"

class WeatherRestApi(
    private val weatherService: WeatherService
) : WeatherRemoteGateway {
    override suspend fun getWeather(latitude: Double, longitude: Double, language: String): Weather {
        val response = weatherService.getWeatherAsync(
            latitude = latitude,
            longitude = longitude,
            unit = UNIT,
            language = language
        ).await()
        return WeatherConverter.fromNetwork(response)
    }
}
package com.akerimtay.smartwardrobe.weather.data.api

import com.akerimtay.smartwardrobe.weather.data.WeatherConverter
import com.akerimtay.smartwardrobe.weather.domain.gateway.WeatherRemoteGateway
import com.akerimtay.smartwardrobe.weather.model.Weather

class WeatherRestApi(
    private val weatherService: WeatherService
) : WeatherRemoteGateway {
    override suspend fun getWeather(
        latitude: Float,
        longitude: Float,
        unit: String,
        language: String
    ): Weather {
        val response = weatherService.getWeatherAsync(
            latitude = latitude,
            longitude = longitude,
            unit = unit,
            language = language
        ).await()
        return WeatherConverter.fromNetwork(response)
    }
}
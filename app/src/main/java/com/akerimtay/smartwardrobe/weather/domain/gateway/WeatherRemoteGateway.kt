package com.akerimtay.smartwardrobe.weather.domain.gateway

import com.akerimtay.smartwardrobe.weather.model.Weather

interface WeatherRemoteGateway {
    suspend fun getWeather(
        latitude: Float,
        longitude: Float,
        unit: String,
        language: String
    ): Weather
}
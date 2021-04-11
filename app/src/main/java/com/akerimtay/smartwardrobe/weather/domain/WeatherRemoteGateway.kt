package com.akerimtay.smartwardrobe.weather.domain

import com.akerimtay.smartwardrobe.weather.model.Weather

interface WeatherRemoteGateway {
    suspend fun getWeather(latitude: Double, longitude: Double, language: String): Weather
}
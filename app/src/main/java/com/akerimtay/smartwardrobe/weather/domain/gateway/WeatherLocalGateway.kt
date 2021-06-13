package com.akerimtay.smartwardrobe.weather.domain.gateway

import com.akerimtay.smartwardrobe.weather.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherLocalGateway {
    suspend fun save(weather: Weather)
    fun getCurrent(): Flow<Weather?>
    suspend fun deleteCurrent()
}
package com.akerimtay.smartwardrobe.weather.domain.gateway

import androidx.lifecycle.LiveData
import com.akerimtay.smartwardrobe.weather.model.Weather

interface WeatherLocalGateway {
    suspend fun save(weather: Weather)
    suspend fun getCurrent(): LiveData<Weather?>
    suspend fun deleteCurrent()
}
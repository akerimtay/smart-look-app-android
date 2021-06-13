package com.akerimtay.smartwardrobe.weather.data.db

import com.akerimtay.smartwardrobe.weather.data.WeatherConverter
import com.akerimtay.smartwardrobe.weather.domain.gateway.WeatherLocalGateway
import com.akerimtay.smartwardrobe.weather.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeatherDatabase(
    private val weatherDao: WeatherDao,
) : WeatherLocalGateway {
    override suspend fun save(weather: Weather) {
        weatherDao.save(WeatherConverter.toDatabase(weather))
    }

    override fun getCurrent(): Flow<Weather?> =
        weatherDao.getByIdAsFlow(WeatherEntity.WEATHER_ID)
            .map { weatherEntity -> weatherEntity?.let { WeatherConverter.fromDatabase(it) } }

    override suspend fun deleteCurrent() {
        weatherDao.deleteById(WeatherEntity.WEATHER_ID)
    }
}
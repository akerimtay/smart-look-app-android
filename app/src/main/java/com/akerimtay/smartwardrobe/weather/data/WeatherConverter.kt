package com.akerimtay.smartwardrobe.weather.data

import com.akerimtay.smartwardrobe.weather.data.api.model.WeatherResponse
import com.akerimtay.smartwardrobe.weather.data.db.WeatherEntity
import com.akerimtay.smartwardrobe.weather.model.Weather

object WeatherConverter {
    fun fromNetwork(response: WeatherResponse): Weather =
        Weather(
            cityName = response.cityName,
            countryCode = response.sys.countryCode,
            longitude = response.location.longitude,
            latitude = response.location.latitude,
            description = response.weather.first().description,
            icon = response.weather.first().icon,
            temperature = response.main.temperature,
            feelsLike = response.main.feelsLike,
            date = response.date
        )

    fun fromDatabase(entity: WeatherEntity): Weather =
        Weather(
            cityName = entity.cityName,
            countryCode = entity.countryCode,
            longitude = entity.longitude,
            latitude = entity.latitude,
            description = entity.description,
            icon = entity.icon,
            temperature = entity.temperature,
            feelsLike = entity.feelsLike,
            date = entity.date
        )

    fun toDatabase(weather: Weather): WeatherEntity =
        WeatherEntity(
            id = WeatherEntity.WEATHER_ID,
            cityName = weather.cityName,
            countryCode = weather.countryCode,
            longitude = weather.longitude,
            latitude = weather.latitude,
            description = weather.description,
            icon = weather.icon,
            temperature = weather.temperature,
            feelsLike = weather.feelsLike,
            date = weather.date
        )
}
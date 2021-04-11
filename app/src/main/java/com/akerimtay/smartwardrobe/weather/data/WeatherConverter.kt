package com.akerimtay.smartwardrobe.weather.data

import com.akerimtay.smartwardrobe.weather.data.api.model.WeatherResponse
import com.akerimtay.smartwardrobe.weather.model.Weather

object WeatherConverter {
    fun fromNetwork(response: WeatherResponse): Weather =
        Weather(
            id = response.id,
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
}
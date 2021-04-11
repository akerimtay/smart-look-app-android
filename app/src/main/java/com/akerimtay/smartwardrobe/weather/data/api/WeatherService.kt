package com.akerimtay.smartwardrobe.weather.data.api

import com.akerimtay.smartwardrobe.weather.data.api.model.WeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    fun getWeatherAsync(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") unit: String,
        @Query("lang") language: String
    ): Deferred<WeatherResponse>
}
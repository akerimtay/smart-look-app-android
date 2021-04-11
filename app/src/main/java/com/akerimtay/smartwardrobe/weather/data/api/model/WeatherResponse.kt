package com.akerimtay.smartwardrobe.weather.data.api.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class WeatherResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val cityName: String,
    @SerializedName("coord") val location: LocationResponse,
    @SerializedName("weather") val weather: List<DescriptionResponse>,
    @SerializedName("main") val main: TemperatureResponse,
    @SerializedName("dt") val date: Date,
    @SerializedName("sys") val sys: CountryResponse,
)
package com.akerimtay.smartwardrobe.weather.data.api.model

import com.google.gson.annotations.SerializedName

data class TemperatureResponse(
    @SerializedName("temp") val temperature: Double,
    @SerializedName("feels_like") val feelsLike: Double
)
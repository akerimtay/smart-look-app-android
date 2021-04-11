package com.akerimtay.smartwardrobe.weather.data.api.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("lon") val longitude: Double,
    @SerializedName("lat") val latitude: Double
)
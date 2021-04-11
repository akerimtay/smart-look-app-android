package com.akerimtay.smartwardrobe.weather.data.api.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("lon") val longitude: Float,
    @SerializedName("lat") val latitude: Float
)
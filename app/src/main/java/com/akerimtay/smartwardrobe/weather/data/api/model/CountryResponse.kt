package com.akerimtay.smartwardrobe.weather.data.api.model

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("country") val countryCode: String?
)
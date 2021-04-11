
package com.akerimtay.smartwardrobe.weather.data.api.model

import com.google.gson.annotations.SerializedName

data class DescriptionResponse(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)
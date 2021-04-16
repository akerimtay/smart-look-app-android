package com.akerimtay.smartwardrobe.outfit.data.api

import java.util.Date

data class OutfitResponse(
    val id: String? = null,
    val name: String? = null,
    val gender: String? = null,
    val season: String? = null,
    val temperatureFrom: Int? = null,
    val temperatureTo: Int? = null,
    val createdDate: Date? = null,
)
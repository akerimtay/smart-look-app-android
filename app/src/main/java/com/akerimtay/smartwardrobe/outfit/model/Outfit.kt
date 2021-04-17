package com.akerimtay.smartwardrobe.outfit.model

import java.util.*

data class Outfit(
    val id: Long,
    val name: String,
    val gender: OutfitGender,
    val season: Season,
    val temperatureFrom: Int,
    val temperatureTo: Int,
    val createdDate: Date,
)
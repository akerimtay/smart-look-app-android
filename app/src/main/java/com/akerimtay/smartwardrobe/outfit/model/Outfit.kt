package com.akerimtay.smartwardrobe.outfit.model

import java.util.Date

data class Outfit(
    val id: String,
    val name: String,
    val gender: OutfitGender,
    val season: Season,
    val temperatureFrom: Int,
    val temperatureTo: Int,
    val createdDate: Date,
)
package com.akerimtay.smartwardrobe.weather.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Weather(
    val cityName: String,
    val countryCode: String,
    val longitude: Float,
    val latitude: Float,
    val description: String,
    val icon: String,
    val temperature: Double,
    val feelsLike: Double,
    val date: Date,
) : Parcelable
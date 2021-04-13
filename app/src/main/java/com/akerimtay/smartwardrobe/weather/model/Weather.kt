package com.akerimtay.smartwardrobe.weather.model

import android.os.Parcelable
import com.akerimtay.smartwardrobe.R
import java.util.Date
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val cityName: String,
    val countryCode: String?,
    val longitude: Float,
    val latitude: Float,
    val description: String,
    val icon: String,
    val temperature: Double,
    val feelsLike: Double,
    val date: Date,
) : Parcelable {
    val iconResId: Int
        get() = when (icon) {
            "01d" -> R.drawable.i01d_high
            "01n" -> R.drawable.i01n_high
            "02d" -> R.drawable.i02d_high
            "02n" -> R.drawable.i02n_high
            "03d", "03n", "04d", "04n" -> R.drawable.i03_high
            "09d", "09n" -> R.drawable.i09_high
            "10d" -> R.drawable.i10d_high
            "10n" -> R.drawable.i10n_high
            "11d" -> R.drawable.i11d_high
            "11n" -> R.drawable.i11n_high
            "13d", "13n" -> R.drawable.i13_high
            else -> R.drawable.i01d_high
        }
}
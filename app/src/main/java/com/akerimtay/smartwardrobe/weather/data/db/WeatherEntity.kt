package com.akerimtay.smartwardrobe.weather.data.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = WeatherEntity.TABLE_NAME)
data class WeatherEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = ID)
    val id: Int,
    @ColumnInfo(name = CITY_NAME)
    val cityName: String,
    @ColumnInfo(name = COUNTRY_CODE)
    val countryCode: String?,
    @ColumnInfo(name = LONGITUDE)
    val longitude: Float,
    @ColumnInfo(name = LATITUDE)
    val latitude: Float,
    @ColumnInfo(name = DESCRIPTION)
    val description: String,
    @ColumnInfo(name = ICON)
    val icon: String,
    @ColumnInfo(name = TEMPERATURE)
    val temperature: Double,
    @ColumnInfo(name = FEELS_LIKE)
    val feelsLike: Double,
    @ColumnInfo(name = DATE)
    val date: Date,
) {
    companion object {
        const val TABLE_NAME = "weathers"
        const val WEATHER_ID = 1
        const val ID = "id"
        const val CITY_NAME = "city_name"
        const val COUNTRY_CODE = "country_code"
        const val LONGITUDE = "longitude"
        const val LATITUDE = "latitude"
        const val DESCRIPTION = "description"
        const val ICON = "icon"
        const val TEMPERATURE = "temperature"
        const val FEELS_LIKE = "feels_like"
        const val DATE = "date"
    }
}
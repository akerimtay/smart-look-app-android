package com.akerimtay.smartwardrobe.database

import androidx.room.TypeConverter
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import com.akerimtay.smartwardrobe.outfit.model.Season
import com.akerimtay.smartwardrobe.user.model.Gender
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = if (value == null) null else Date(value)

    @TypeConverter
    fun toTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromGender(gender: Gender): String = gender.serializedName

    @TypeConverter
    fun toGender(value: String?): Gender = Gender.toGender(value)

    @TypeConverter
    fun fromOutfitGender(gender: OutfitGender): String = gender.serializedName

    @TypeConverter
    fun toOutfitGender(value: String?): OutfitGender = OutfitGender.toGender(value)

    @TypeConverter
    fun fromSeason(season: Season): String = season.serializedName

    @TypeConverter
    fun toSeason(value: String?): Season = Season.toSeason(value)
}
package com.akerimtay.smartwardrobe.database

import androidx.room.TypeConverter
import com.akerimtay.smartwardrobe.user.model.Gender
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = if (value == null) null else Date(value)

    @TypeConverter
    fun toTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromGender(gender: Gender): String = gender.serializedName

    @TypeConverter
    fun toGender(value: String?): Gender = Gender.toGender(value)
}
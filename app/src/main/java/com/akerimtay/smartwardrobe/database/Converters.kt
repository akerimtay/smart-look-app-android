package com.akerimtay.smartwardrobe.database

import androidx.room.TypeConverter
import com.akerimtay.smartwardrobe.user.model.Gender
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun genderToString(gender: Gender): String = gender.serializedName

    @TypeConverter
    fun stringToGender(value: String?): Gender = Gender.toGender(value)
}
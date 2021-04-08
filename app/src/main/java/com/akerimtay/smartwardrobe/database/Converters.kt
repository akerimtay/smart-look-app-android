package com.akerimtay.smartwardrobe.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.akerimtay.smartwardrobe.user.model.Gender
import java.io.ByteArrayOutputStream
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

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}
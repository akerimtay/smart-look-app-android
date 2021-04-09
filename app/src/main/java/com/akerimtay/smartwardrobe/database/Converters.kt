package com.akerimtay.smartwardrobe.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
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

    fun bitmapToByteArray(bitmap: Bitmap?): ByteArray? = bitmap?.let {
        val outputStream = ByteArrayOutputStream()
        it.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.toByteArray()
    }

    fun byteArrayToBitmap(byteArray: ByteArray?): Bitmap? = byteArray?.let {
        BitmapFactory.decodeByteArray(it, 0, it.size)
    }

    @TypeConverter
    fun bitmapToString(bitmap: Bitmap?): String? = bitmap?.let {
        val byteArray = bitmapToByteArray(bitmap)
        Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    @TypeConverter
    fun stringToBitmap(value: String?): Bitmap? = value?.let {
        val encodeByte = Base64.decode(value, Base64.DEFAULT)
        byteArrayToBitmap(encodeByte)
    }
}
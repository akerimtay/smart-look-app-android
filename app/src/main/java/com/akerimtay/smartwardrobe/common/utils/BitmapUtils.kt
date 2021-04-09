package com.akerimtay.smartwardrobe.common.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

object BitmapUtils {
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    fun toBitmap(byteArray: ByteArray): Bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}
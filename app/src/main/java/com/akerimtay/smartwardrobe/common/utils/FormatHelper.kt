package com.akerimtay.smartwardrobe.common.utils

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "dd.MM.yyyy"

object FormatHelper {
    private val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US)

    fun getDate(date: Date?): String = date?.let { simpleDateFormat.format(it) } ?: ""
}
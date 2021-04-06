package com.akerimtay.smartwardrobe.common.utils

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "dd MMMM yyyy"

object FormatHelper {
    private val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale("ru"))

    fun getDate(date: Date?): String = date?.let { simpleDateFormat.format(it) } ?: ""
}
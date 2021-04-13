package com.akerimtay.smartwardrobe.common.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val DATE_FORMAT = "dd MMMM yyyy"
private const val DAY_MONTH_FORMAT = "dd MMMM"

object FormatHelper {
    private val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale("ru"))
    private val dayMonthFormat = SimpleDateFormat(DAY_MONTH_FORMAT, Locale("ru"))

    fun getDate(date: Date?): String = date?.let { simpleDateFormat.format(it) } ?: ""

    fun getDayMonth(date: Date?): String = date?.let { dayMonthFormat.format(it) } ?: ""
}
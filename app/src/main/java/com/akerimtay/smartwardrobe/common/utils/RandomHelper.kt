package com.akerimtay.smartwardrobe.common.utils

import java.util.Random

object RandomHelper {
    fun generateRandomNumbers(length: Int = 6): Long {
        val builder = StringBuilder()
        for (i in 0 until length) {
            val random = Random()
            builder.append(random.nextInt(8) + 1)
        }
        return builder.toString().toLong()
    }
}
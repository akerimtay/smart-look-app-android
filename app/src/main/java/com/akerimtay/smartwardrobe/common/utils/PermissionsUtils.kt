package com.akerimtay.smartwardrobe.common.utils

import android.content.pm.PackageManager

object PermissionsUtils {
    fun verifyGrantResults(grantResults: IntArray): Boolean {
        grantResults.forEach {
            if (it == PackageManager.PERMISSION_DENIED) return false
        }
        return true
    }
}
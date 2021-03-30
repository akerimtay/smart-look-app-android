package com.akerimtay.smartwardrobe.common.persistence

interface PreferencesContract {
    var userId: String?

    fun clearSession()
}
package com.akerimtay.smartwardrobe.common.persistence

interface PreferencesContract {
    var userId: String?
    var longitude: Float
    var latitude: Float

    fun clearSession()
}
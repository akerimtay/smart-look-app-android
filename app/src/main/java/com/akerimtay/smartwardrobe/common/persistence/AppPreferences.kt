package com.akerimtay.smartwardrobe.common.persistence

import android.content.Context
import android.content.SharedPreferences

private const val PREF_FILE_NAME = "APP_PREFERENCES"
private const val PREF_KEY_USER_ID = "PREF_KEY_USER_ID"

class AppPreferences(context: Context) : PreferencesContract {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)

    override var userId: String?
        get() = prefs.getString(PREF_KEY_USER_ID, null)
        set(value) {
            prefs.edit().putString(PREF_KEY_USER_ID, value).apply()
        }

    override fun clearSession() {
        val editor = prefs.edit()
        editor.remove(PREF_KEY_USER_ID)
        editor.apply()
    }
}
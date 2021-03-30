package com.akerimtay.smartwardrobe.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import com.akerimtay.smartwardrobe.common.persistence.PreferencesContract

class SessionManager(
    private val preferences: PreferencesContract
) {
    var userId: String?
        get() = preferences.userId
        set(value) {
            preferences.userId = value
            _isLoggedIn.postValue(isAuthorized)
        }
    private val isAuthorized: Boolean
        get() = !userId.isNullOrEmpty()

    private val _isLoggedIn = MutableLiveData(isAuthorized)
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn.distinctUntilChanged()

    fun clearSession() {
        preferences.clearSession()
        _isLoggedIn.postValue(isAuthorized)
    }
}
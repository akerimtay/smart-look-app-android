package com.akerimtay.smartwardrobe.splash.ui

import androidx.lifecycle.LiveData
import com.akerimtay.smartwardrobe.auth.SessionManager
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import timber.log.Timber

class SplashViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {
    private val _actions = SingleLiveEvent<SplashAction>()
    val actions: LiveData<SplashAction> = _actions

    init {
        launchSafe(
            body = {
                val startPage = if (sessionManager.isAuthorized) StartPage.MAIN else StartPage.AUTH
                _actions.postValue(SplashAction.ShowStartPage(startPage = startPage))
            },
            handleError = { Timber.e(it) }
        )
    }
}

enum class StartPage {
    AUTH, MAIN
}

sealed class SplashAction : Action {
    data class ShowStartPage(val startPage: StartPage) : SplashAction()
}
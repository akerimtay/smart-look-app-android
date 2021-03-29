package com.akerimtay.smartwardrobe.splash.ui

import androidx.lifecycle.LiveData
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import timber.log.Timber

class SplashViewModel : BaseViewModel() {
    private val _actions = SingleLiveEvent<SplashAction>()
    val actions: LiveData<SplashAction> = _actions

    init {
        launchSafe(
            body = { _actions.postValue(SplashAction.ShowStartPage(startPage = StartPage.AUTH)) },
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
package com.akerimtay.smartwardrobe.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akerimtay.smartwardrobe.auth.domain.LogOutUseCase
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import timber.log.Timber

class ProfileViewModel(
    private val logOutUseCase: LogOutUseCase
) : BaseViewModel() {
    private val _actions = SingleLiveEvent<ProfileAction>()
    val actions: LiveData<ProfileAction> = _actions

    private val _progressLoading = MutableLiveData(false)
    val progressLoading: LiveData<Boolean> = _progressLoading

    fun logOut() {
        launchSafe(
            start = { _progressLoading.postValue(true) },
            finish = { _progressLoading.postValue(false) },
            body = {
                logOutUseCase(Unit)
                _actions.postValue(ProfileAction.ShowLoginScreen)
            },
            handleError = {
                Timber.e(it, "Cannot logout")
            }
        )
    }
}

sealed class ProfileAction : Action {
    object ShowLoginScreen : ProfileAction()
}
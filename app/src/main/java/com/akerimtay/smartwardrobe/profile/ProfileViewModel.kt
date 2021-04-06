package com.akerimtay.smartwardrobe.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.akerimtay.smartwardrobe.auth.domain.LogOutUseCase
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import com.akerimtay.smartwardrobe.user.domain.GetCurrentUserUseCase
import timber.log.Timber

class ProfileViewModel(
    private val logOutUseCase: LogOutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : BaseViewModel() {
    private val _actions = SingleLiveEvent<ProfileAction>()
    val actions: LiveData<ProfileAction> = _actions

    private val _progressLoading = MutableLiveData(false)
    val progressLoading: LiveData<Boolean> = _progressLoading

    val currentUser = liveData { emitSource(getCurrentUserUseCase(Unit)) }

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
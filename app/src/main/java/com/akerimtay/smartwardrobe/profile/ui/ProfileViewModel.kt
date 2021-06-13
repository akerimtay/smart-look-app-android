package com.akerimtay.smartwardrobe.profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.auth.domain.LogOutUseCase
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import com.akerimtay.smartwardrobe.common.model.ErrorMessage
import com.akerimtay.smartwardrobe.common.utils.getErrorMessage
import com.akerimtay.smartwardrobe.user.domain.GetCurrentUserAsFlowUseCase
import com.akerimtay.smartwardrobe.user.domain.LoadCurrentUserUseCase
import kotlinx.coroutines.delay
import timber.log.Timber

private const val DELAY_TIME_IN_MILLIS = 2_000L

class ProfileViewModel(
    private val logOutUseCase: LogOutUseCase,
    getCurrentUserAsFlowUseCase: GetCurrentUserAsFlowUseCase,
    private val loadCurrentUserUseCase: LoadCurrentUserUseCase,
) : BaseViewModel() {
    private val _actions = SingleLiveEvent<ProfileAction>()
    val actions: LiveData<ProfileAction> = _actions

    private val _progressLoading = MutableLiveData(false)
    val progressLoading: LiveData<Boolean> = _progressLoading

    private val _swipeRefreshLoading = MutableLiveData(false)
    val swipeRefreshLoading: LiveData<Boolean> = _swipeRefreshLoading

    val currentUser = getCurrentUserAsFlowUseCase(Unit).asLiveData()

    init {
        loadUser()
    }

    fun loadUser() {
        launchSafe(
            start = { _swipeRefreshLoading.postValue(true) },
            finish = { _swipeRefreshLoading.postValue(false) },
            body = { loadCurrentUserUseCase(Unit) },
            handleError = {
                Timber.e(it, "Can't load user")
                _actions.postValue(
                    ProfileAction.ShowErrorMessage(it.getErrorMessage(R.string.error_load_user))
                )
            }
        )
    }

    fun logOut() {
        launchSafe(
            start = { _progressLoading.postValue(true) },
            finish = { _progressLoading.postValue(false) },
            body = {
                logOutUseCase(Unit)
                delay(timeMillis = DELAY_TIME_IN_MILLIS)
                _actions.postValue(ProfileAction.ShowLoginScreen)
            },
            handleError = {
                Timber.e(it, "Cannot logout")
                _actions.postValue(
                    ProfileAction.ShowErrorMessage(it.getErrorMessage(R.string.error_log_out))
                )
            }
        )
    }
}

sealed class ProfileAction : Action {
    data class ShowErrorMessage(val errorMessage: ErrorMessage) : ProfileAction()
    object ShowLoginScreen : ProfileAction()
}
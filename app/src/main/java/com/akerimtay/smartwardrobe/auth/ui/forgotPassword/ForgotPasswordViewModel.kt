package com.akerimtay.smartwardrobe.auth.ui.forgotPassword

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.auth.AuthValidator
import com.akerimtay.smartwardrobe.auth.domain.RestorePasswordUseCase
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseError
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import timber.log.Timber

class ForgotPasswordViewModel(
    private val restorePasswordUseCase: RestorePasswordUseCase
) : BaseViewModel() {
    private val _actions = SingleLiveEvent<ForgotPasswordAction>()
    val actions: LiveData<ForgotPasswordAction> = _actions

    private val _progressLoading = MutableLiveData(false)
    val progressLoading: LiveData<Boolean> = _progressLoading

    fun restorePassword(email: String) {
        val errorActions = mutableListOf<ForgotPasswordAction>()

        if (!AuthValidator.emailValidator.isValid(email)) {
            errorActions.add(
                ForgotPasswordAction.ShowFieldError(
                    field = ForgotPasswordViewModel.Field.EMAIL,
                    errorMessageId = if (email.isBlank()) R.string.required_field else R.string.invalid_email
                )
            )
        }

        if (errorActions.isEmpty()) {
            launchSafe(
                start = { _progressLoading.postValue(true) },
                finish = { _progressLoading.postValue(false) },
                body = {
                    restorePasswordUseCase(RestorePasswordUseCase.Param(email = email))
                    _actions.postValue(ForgotPasswordAction.ShowSignInScreen)
                },
                handleError = {
                    Timber.e(it, "Error while sign up")
                    _actions.postValue(
                        when (it) {
                            is BaseError -> ForgotPasswordAction.ShowMessage(it.errorResId)
                            is FirebaseAuthInvalidUserException -> ForgotPasswordAction.ShowMessage(R.string.error_user_not_found)
                            else -> ForgotPasswordAction.ShowMessage(R.string.error_auth)
                        }
                    )
                }
            )
        } else {
            errorActions.forEach { _actions.value = it }
        }
    }

    enum class Field {
        EMAIL
    }
}

sealed class ForgotPasswordAction : Action {
    data class ShowFieldError(
        val field: ForgotPasswordViewModel.Field,
        @StringRes val errorMessageId: Int = 0
    ) : ForgotPasswordAction()

    data class ShowMessage(@StringRes val errorResId: Int) : ForgotPasswordAction()
    object ShowSignInScreen : ForgotPasswordAction()
}
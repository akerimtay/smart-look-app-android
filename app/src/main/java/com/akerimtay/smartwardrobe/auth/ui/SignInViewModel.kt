package com.akerimtay.smartwardrobe.auth.ui

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.auth.AuthValidator
import com.akerimtay.smartwardrobe.auth.domain.SignInUseCase
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseError
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import timber.log.Timber

class SignInViewModel(
    private val signInUseCase: SignInUseCase
) : BaseViewModel() {
    private val _actions = SingleLiveEvent<SignInAction>()
    val actions: LiveData<SignInAction> = _actions

    private val _progressLoading = MutableLiveData(false)
    val progressLoading: LiveData<Boolean> = _progressLoading

    fun signIn(email: String, password: String) {
        val errorActions = mutableListOf<SignInAction>()

        if (!AuthValidator.emailValidator.isValid(email)) {
            errorActions.add(
                SignInAction.ShowFieldError(
                    field = Field.EMAIL,
                    errorMessageId = R.string.invalid_email
                )
            )
        }
        if (!AuthValidator.passwordValidator.isValid(password)) {
            errorActions.add(
                SignInAction.ShowFieldError(
                    field = Field.PASSWORD,
                    errorMessageId = R.string.invalid_password
                )
            )
        }

        if (errorActions.isEmpty()) {
            launchSafe(
                start = { _progressLoading.postValue(true) },
                finish = { _progressLoading.postValue(false) },
                body = {
                    signInUseCase(SignInUseCase.Param(email = email, password = password))
                },
                handleError = {
                    Timber.e(it, "Error while sign in")
                    _actions.postValue(
                        when (it) {
                            is BaseError.NoInternetError -> SignInAction.ShowMessage(it.errorResId)
                            is BaseError.InvalidEmailOrPasswordError -> SignInAction.ShowMessage(it.errorResId)
                            is FirebaseAuthInvalidCredentialsException -> SignInAction.ShowMessage(R.string.invalid_email_or_password)
                            else -> SignInAction.ShowMessage(R.string.error_auth)
                        }
                    )
                }
            )
        } else {
            errorActions.forEach { _actions.value = it }
        }
    }

    enum class Field {
        EMAIL, PASSWORD
    }
}

sealed class SignInAction : Action {
    data class ShowFieldError(
        val field: SignInViewModel.Field,
        @StringRes val errorMessageId: Int = 0
    ) : SignInAction()

    data class ShowMessage(@StringRes val errorResId: Int) : SignInAction()
}
package com.akerimtay.smartwardrobe.auth.ui.signUp

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.auth.AuthValidator
import com.akerimtay.smartwardrobe.auth.domain.SignUpUseCase
import com.akerimtay.smartwardrobe.auth.model.Gender
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseError
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import timber.log.Timber
import java.util.*

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel() {

    private val _actions = SingleLiveEvent<SignUpAction>()
    val actions: LiveData<SignUpAction> = _actions

    private val _progressLoading = MutableLiveData(false)
    val progressLoading: LiveData<Boolean> = _progressLoading

    private val _selectedGender = MutableLiveData<Gender?>()
    val selectedGender: LiveData<Gender?> = _selectedGender

    private val _selectedBirthDate = MutableLiveData<Date?>()
    val selectedBirthDate: LiveData<Date?> = _selectedBirthDate

    fun selectGender(gender: Gender?) {
        _selectedGender.value = gender
    }

    fun selectBirthDate(date: Date?) {
        _selectedBirthDate.value = date
    }

    fun signUp(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val errorActions = mutableListOf<SignUpAction>()

        if (!AuthValidator.nameValidator.isValid(name)) {
            errorActions.add(
                SignUpAction.ShowFieldError(
                    field = Field.NAME,
                    errorMessageId = if (name.isBlank()) R.string.required_field else R.string.name_should_be_more
                )
            )
        }
        if (!AuthValidator.emailValidator.isValid(email)) {
            errorActions.add(
                SignUpAction.ShowFieldError(
                    field = Field.EMAIL,
                    errorMessageId = if (email.isBlank()) R.string.required_field else R.string.invalid_email
                )
            )
        }
        if (!AuthValidator.passwordValidator.isValid(password)) {
            errorActions.add(
                SignUpAction.ShowFieldError(
                    field = Field.PASSWORD,
                    errorMessageId = if (password.isBlank()) R.string.required_field else R.string.invalid_password
                )
            )
        }
        if (confirmPassword.isBlank() || confirmPassword != password) {
            errorActions.add(
                SignUpAction.ShowFieldError(
                    field = Field.CONFIRM_PASSWORD,
                    errorMessageId = if (confirmPassword.isBlank()) R.string.required_field else R.string.not_equal_passwords
                )
            )
        }

        if (errorActions.isEmpty()) {
            launchSafe(
                start = { _progressLoading.postValue(true) },
                finish = { _progressLoading.postValue(false) },
                body = {
                    signUpUseCase(
                        SignUpUseCase.Param(
                            name = name,
                            gender = selectedGender.value ?: Gender.MALE,
                            email = email,
                            birthDate = selectedBirthDate.value,
                            password = password
                        )
                    )
                    _actions.postValue(SignUpAction.ShowSignInScreen)
                },
                handleError = {
                    Timber.e(it, "Error while sign up")
                    _actions.postValue(
                        when (it) {
                            is BaseError -> SignUpAction.ShowMessage(it.errorResId)
                            is FirebaseAuthUserCollisionException -> SignUpAction.ShowMessage(R.string.error_auth_user_collision)
                            else -> SignUpAction.ShowMessage(R.string.error_auth)
                        }
                    )
                }
            )
        } else {
            errorActions.forEach { _actions.value = it }
        }
    }

    enum class Field {
        NAME, EMAIL, PASSWORD, CONFIRM_PASSWORD
    }
}

sealed class SignUpAction : Action {
    data class ShowFieldError(
        val field: SignUpViewModel.Field,
        @StringRes val errorMessageId: Int = 0
    ) : SignUpAction()

    data class ShowMessage(@StringRes val errorResId: Int) : SignUpAction()
    object ShowSignInScreen : SignUpAction()
}
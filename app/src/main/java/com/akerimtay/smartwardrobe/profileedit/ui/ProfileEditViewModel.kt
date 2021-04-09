package com.akerimtay.smartwardrobe.profileedit.ui

import android.graphics.Bitmap
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseError
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import com.akerimtay.smartwardrobe.profileedit.ProfileEditValidator
import com.akerimtay.smartwardrobe.user.domain.GetCurrentUserUseCase
import com.akerimtay.smartwardrobe.user.domain.UpdateUserUseCase
import timber.log.Timber
import java.util.*

class ProfileEditViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : BaseViewModel() {
    private val _actions = SingleLiveEvent<ProfileEditAction>()
    val actions: LiveData<ProfileEditAction> = _actions

    private val _progressLoading = MutableLiveData(false)
    val progressLoading: LiveData<Boolean> = _progressLoading

    private val _selectedBirthDate = MutableLiveData<Date?>()
    val selectedBirthDate: LiveData<Date?> = _selectedBirthDate

    private val _selectedImage = MutableLiveData<Bitmap?>()
    val selectedImage: LiveData<Bitmap?> = _selectedImage

    val currentUser = liveData { emitSource(getCurrentUserUseCase(Unit)) }

    fun selectBirthDate(date: Date?) {
        _selectedBirthDate.value = date
    }

    fun selectImage(bitmap: Bitmap?) {
        _selectedImage.value = bitmap
    }

    fun save(name: String) {
        val errorActions = mutableListOf<ProfileEditAction>()

        if (!ProfileEditValidator.nameValidator.isValid(name)) {
            errorActions.add(
                ProfileEditAction.ShowFieldError(
                    field = Field.NAME,
                    errorMessageId = R.string.required_field
                )
            )
        }

        if (errorActions.isEmpty()) {
            launchSafe(
                start = { _progressLoading.postValue(true) },
                finish = { _progressLoading.postValue(false) },
                body = {
                    val user = currentUser.value?.copy(
                        name = name,
                        birthDate = selectedBirthDate.value,
                        image = selectedImage.value
                    ) ?: throw BaseError.UserNotFound
                    updateUserUseCase(UpdateUserUseCase.Param(user = user))
                    _actions.postValue(ProfileEditAction.ShowPreviousScreen)
                },
                handleError = {
                    Timber.e(it, "Error while save user data")
                    _actions.postValue(
                        when (it) {
                            is BaseError -> ProfileEditAction.ShowMessage(it.errorResId)
                            else -> ProfileEditAction.ShowMessage(R.string.error_auth)
                        }
                    )
                }
            )
        } else {
            errorActions.forEach { _actions.value = it }
        }
    }

    enum class Field {
        NAME
    }
}

sealed class ProfileEditAction : Action {
    data class ShowFieldError(
        val field: ProfileEditViewModel.Field,
        @StringRes val errorMessageId: Int
    ) : ProfileEditAction()

    data class ShowMessage(@StringRes val errorResId: Int) : ProfileEditAction()
    object ShowPreviousScreen : ProfileEditAction()
}
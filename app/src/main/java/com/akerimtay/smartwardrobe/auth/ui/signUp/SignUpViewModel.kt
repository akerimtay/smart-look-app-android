package com.akerimtay.smartwardrobe.auth.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akerimtay.smartwardrobe.auth.domain.SignUpUseCase
import com.akerimtay.smartwardrobe.auth.model.Gender
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import java.util.*

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel() {

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

    }
}
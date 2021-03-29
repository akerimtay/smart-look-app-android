package com.akerimtay.smartwardrobe.auth.ui.signUp

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.auth.model.Gender
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.utils.*
import com.akerimtay.smartwardrobe.databinding.FragmentSignUpBinding
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {
    private val binding: FragmentSignUpBinding by viewBinding()
    private val viewModel: SignUpViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            emailEditText.doAfterTextChanged { emailTextInputLayout.isErrorEnabled = false }
            nameEditText.doAfterTextChanged { nameTextInputLayout.isErrorEnabled = false }
            passwordEditText.doAfterTextChanged { passwordTextInputLayout.isErrorEnabled = false }
            confirmPasswordEditText.doAfterTextChanged {
                confirmPasswordTextInputLayout.isErrorEnabled = false
            }
            birthDateEditText.doAfterTextChanged { birthDateTextInputLayout.isErrorEnabled = false }
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
            genderRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                viewModel.selectGender(
                    when (checkedId) {
                        R.id.male_radio_button -> Gender.MALE
                        R.id.female_radio_button -> Gender.FEMALE
                        else -> null
                    }
                )
            }
            maleRadioButton.isChecked = true
            birthDateButton.setThrottleOnClickListener {
                SingleDateAndTimePickerDialog.Builder(context)
                    .bottomSheet()
                    .curved()
                    .backgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
                    )
                    .mainColor(ContextCompat.getColor(requireContext(), R.color.colorWhite))
                    .displayHours(false)
                    .displayMinutes(false)
                    .displayDays(false)
                    .displayMonth(true)
                    .displayDaysOfMonth(true)
                    .displayYears(true)
                    .displayMonthNumbers(true)
                    .title(getString(R.string.enter_birth_date))
                    .titleTextColor(ContextCompat.getColor(requireContext(), R.color.colorWhite))
                    .listener { selectedDate ->
                        viewModel.selectBirthDate(selectedDate)
                    }
                    .display()
            }
            signUpButton.setThrottleOnClickListener {
                hideKeyboard()
                viewModel.signUp(
                    name = nameEditText.editableText.trim().toString(),
                    email = emailEditText.editableText.trim().toString(),
                    password = passwordEditText.editableText.trim().toString(),
                    confirmPassword = confirmPasswordEditText.editableText.trim().toString()
                )
            }
        }

        viewModel.progressLoading.observeNotNull(viewLifecycleOwner) { binding.progressStateView.isVisible = it }
        viewModel.selectedBirthDate.observeNotNull(viewLifecycleOwner) { date ->
            binding.birthDateEditText.setText(FormatHelper.getDate(date))
        }
        viewModel.actions.observeNotNull(viewLifecycleOwner) { action ->
            when (action) {
                is SignUpAction.ShowFieldError -> showTextFieldError(
                    textInputLayout = when (action.field) {
                        SignUpViewModel.Field.NAME -> binding.nameTextInputLayout
                        SignUpViewModel.Field.EMAIL -> binding.emailTextInputLayout
                        SignUpViewModel.Field.PASSWORD -> binding.passwordTextInputLayout
                        SignUpViewModel.Field.CONFIRM_PASSWORD -> binding.confirmPasswordTextInputLayout
                    },
                    message = getString(action.errorMessageId)
                )
                is SignUpAction.ShowMessage -> showToast(messageResId = action.errorResId)
                is SignUpAction.ShowSignInScreen -> findNavController().popBackStack()
            }
        }
    }

    override fun isActionBarVisible(): Boolean = false

    override fun isBottomNavigationViewVisible(): Boolean = false

    private fun showTextFieldError(textInputLayout: TextInputLayout, message: String) {
        textInputLayout.error = message
        textInputLayout.isErrorEnabled = true
    }
}
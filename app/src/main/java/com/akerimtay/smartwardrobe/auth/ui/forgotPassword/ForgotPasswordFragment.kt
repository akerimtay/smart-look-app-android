package com.akerimtay.smartwardrobe.auth.ui.forgotPassword

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.utils.hideKeyboard
import com.akerimtay.smartwardrobe.common.utils.observeNotNull
import com.akerimtay.smartwardrobe.common.utils.setThrottleOnClickListener
import com.akerimtay.smartwardrobe.common.utils.showToast
import com.akerimtay.smartwardrobe.databinding.FragmentForgotPasswordBinding
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordFragment : BaseFragment(R.layout.fragment_forgot_password) {
    private val binding: FragmentForgotPasswordBinding by viewBinding()
    private val viewModel: ForgotPasswordViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
            emailEditText.doAfterTextChanged { emailTextInputLayout.isErrorEnabled = false }
            emailEditText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) restorePasswordButton.callOnClick()
                return@setOnEditorActionListener false
            }
            restorePasswordButton.setThrottleOnClickListener {
                hideKeyboard()
                viewModel.restorePassword(email = emailEditText.editableText.trim().toString())
            }
        }

        viewModel.progressLoading.observeNotNull(viewLifecycleOwner) { binding.progressStateView.isVisible = it }
        viewModel.actions.observeNotNull(viewLifecycleOwner) { action ->
            when (action) {
                is ForgotPasswordAction.ShowFieldError -> showTextFieldError(
                    textInputLayout = when (action.field) {
                        ForgotPasswordViewModel.Field.EMAIL -> binding.emailTextInputLayout
                    },
                    message = getString(action.errorMessageId)
                )
                is ForgotPasswordAction.ShowMessage -> showToast(messageResId = action.errorResId)
                is ForgotPasswordAction.ShowSignInScreen -> {
                    showToast(messageResId = R.string.mail_for_restore_password_sent)
                    findNavController().popBackStack()
                }
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
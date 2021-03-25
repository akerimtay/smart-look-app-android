package com.akerimtay.smartwardrobe.auth.ui.signIn

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.MainActivity
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.auth.ui.signUp.SignUpFragment
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.utils.*
import com.akerimtay.smartwardrobe.databinding.FragmentSignInBinding
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {
    private val binding: FragmentSignInBinding by viewBinding()
    private val viewModel: SignInViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            emailEditText.doAfterTextChanged { emailTextInputLayout.isErrorEnabled = false }
            passwordEditText.doAfterTextChanged { passwordTextInputLayout.isErrorEnabled = false }
            passwordEditText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) signInButton.callOnClick()
                return@setOnEditorActionListener false
            }

            signInButton.setThrottleOnClickListener {
                hideKeyboard()
                viewModel.signIn(
                    email = emailEditText.editableText.toString(),
                    password = passwordEditText.editableText.toString()
                )
            }
            signUpButton.setThrottleOnClickListener {
                activity?.supportFragmentManager?.replaceFragment(fragment = SignUpFragment())
            }

            emailEditText.showKeyboard()
        }

        viewModel.progressLoading.observeNotNull(viewLifecycleOwner) { binding.progressStateView.isVisible = it }
        viewModel.actions.observeNotNull(viewLifecycleOwner) { action ->
            when (action) {
                is SignInAction.ShowFieldError -> showTextFieldError(
                    textInputLayout = when (action.field) {
                        SignInViewModel.Field.EMAIL -> binding.emailTextInputLayout
                        SignInViewModel.Field.PASSWORD -> binding.passwordTextInputLayout
                    },
                    message = getString(action.errorMessageId)
                )
                is SignInAction.ShowMessage -> showToast(messageResId = action.errorResId)
                is SignInAction.ShowMainScreen -> MainActivity.start(requireContext())
            }
        }
    }

    private fun showTextFieldError(textInputLayout: TextInputLayout, message: String) {
        textInputLayout.error = message
        textInputLayout.isErrorEnabled = true
    }
}
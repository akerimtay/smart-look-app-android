package com.akerimtay.smartwardrobe.auth.ui.signUp

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.databinding.FragmentSignUpBinding
import com.google.android.material.textfield.TextInputLayout

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {
    private val binding: FragmentSignUpBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val adapter = ArrayAdapter(
//            requireContext(),
//            android.R.layout.simple_list_item_single_choice,
//            Gender.values().map { getString(it.displayName) }
//        )
//        MaterialAlertDialogBuilder(requireContext())
//            .setTitle(R.string.select_gender)
//            .setNeutralButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
//            .setPositiveButton(R.string.choose) { dialog, _ -> dialog.dismiss() }
//            .setSingleChoiceItems(adapter, -1) { dialog, position ->
//                val selectedGender = Gender.toGender(adapter.getItem(position))
//
//            }
//            .show()
    }

    private fun showTextFieldError(textInputLayout: TextInputLayout, message: String) {
        textInputLayout.error = message
        textInputLayout.isErrorEnabled = true
    }
}
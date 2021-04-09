package com.akerimtay.smartwardrobe.profileedit.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.model.ActionMenuType
import com.akerimtay.smartwardrobe.common.ui.ActionsDialog
import com.akerimtay.smartwardrobe.common.utils.*
import com.akerimtay.smartwardrobe.databinding.FragmentProfileEditBinding
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileEditFragment : BaseFragment(R.layout.fragment_profile_edit),
    ActionsDialog.ActionsDialogCallback {
    private val binding: FragmentProfileEditBinding by viewBinding()
    private val viewModel: ProfileEditViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_save -> {
                        hideKeyboard()
                        viewModel.save(name = binding.nameEditText.editableText.trim().toString())
                    }
                }
                return@setOnMenuItemClickListener true
            }
            editButton.setThrottleOnClickListener {
                val actionMenuTypes = mutableSetOf(ActionMenuType.CHOOSE_IMAGE_FROM_GALLERY)
                viewModel.currentUser.value?.image?.let {
                    actionMenuTypes.add(ActionMenuType.DELETE_IMAGE)
                }
                ActionsDialog.show(
                    fragmentManager = childFragmentManager,
                    actionMenuTypes = actionMenuTypes
                )
            }
            nameEditText.doAfterTextChanged { nameTextInputLayout.isErrorEnabled = false }
            birthDateButton.setThrottleOnClickListener {
                SingleDateAndTimePickerDialog.Builder(context)
                    .bottomSheet()
                    .curved()
                    .backgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
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
                    .defaultDate(viewModel.selectedBirthDate.value)
                    .listener { selectedDate ->
                        viewModel.selectBirthDate(selectedDate)
                    }
                    .display()
            }
        }

        viewModel.progressLoading.observeNotNull(viewLifecycleOwner) { binding.progressStateView.isVisible = it }
        viewModel.currentUser.observeNotNull(viewLifecycleOwner) { user ->
            user?.let {
                binding.avatarImageView.loadImage(it.image)
                binding.nameEditText.setText(it.name)
                viewModel.selectBirthDate(it.birthDate)
            }
        }
        viewModel.selectedBirthDate.observeNotNull(viewLifecycleOwner) { date ->
            binding.birthDateEditText.setText(FormatHelper.getDate(date))
        }
        viewModel.actions.observeNotNull(viewLifecycleOwner) { action ->
            when (action) {
                is ProfileEditAction.ShowPreviousScreen -> {
                    showToast(R.string.success_saved)
                    findNavController().popBackStack()
                }
                is ProfileEditAction.ShowMessage -> showToast(messageResId = action.errorResId)
                is ProfileEditAction.ShowFieldError -> showTextFieldError(
                    textInputLayout = when (action.field) {
                        ProfileEditViewModel.Field.NAME -> binding.nameTextInputLayout
                    },
                    message = getString(action.errorMessageId)
                )
            }
        }
    }

    override fun onActionMenuClick(actionMenuType: ActionMenuType) {

    }

    private fun showTextFieldError(textInputLayout: TextInputLayout, message: String) {
        textInputLayout.error = message
        textInputLayout.isErrorEnabled = true
    }
}
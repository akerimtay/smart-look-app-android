package com.akerimtay.smartwardrobe.profileedit.ui

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.di.GlideApp
import com.akerimtay.smartwardrobe.common.model.ActionMenuType
import com.akerimtay.smartwardrobe.common.ui.ActionsDialog
import com.akerimtay.smartwardrobe.common.utils.*
import com.akerimtay.smartwardrobe.databinding.FragmentProfileEditBinding
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProfileEditFragment : BaseFragment(R.layout.fragment_profile_edit),
    ActionsDialog.ActionsDialogCallback {
    private val binding: FragmentProfileEditBinding by viewBinding()
    private val viewModel: ProfileEditViewModel by viewModel()

    private val pickLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            runCatching {
                val imageUri: Uri? = result.data?.data
                imageUri?.let {
                    val inputStream = activity?.contentResolver?.openInputStream(it)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    viewModel.uploadImage(bitmap)
                }
            }.onFailure {
                Timber.e(it, "Can't get image from gallery")
                showToast(R.string.error_getting_image_from_gallery)
            }.onSuccess {
                showToast(R.string.start_upload_image)
            }
        }
    }

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
            avatarImageView.setThrottleOnClickListener { editButton.callOnClick() }
            editButton.setThrottleOnClickListener {
                val actionMenuTypes = mutableSetOf(ActionMenuType.CHOOSE_IMAGE_FROM_GALLERY)
                viewModel.selectedImage.value?.let {
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
                binding.nameEditText.setText(it.name)
                viewModel.selectImage(it.imageUrl)
                viewModel.selectBirthDate(it.birthDate)
            }
        }
        viewModel.selectedBirthDate.observe(viewLifecycleOwner) { date ->
            binding.birthDateEditText.setText(FormatHelper.getDate(date))
        }
        viewModel.selectedImage.observe(viewLifecycleOwner) {
            binding.avatarImageView.load(
                glide = GlideApp.with(this),
                imageUrl = it
            )
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
        when (actionMenuType) {
            ActionMenuType.CHOOSE_IMAGE_FROM_GALLERY -> pickLauncher.launch(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI
                )
            )
            ActionMenuType.DELETE_IMAGE -> viewModel.selectImage(value = null)
        }
    }

    private fun showTextFieldError(textInputLayout: TextInputLayout, message: String) {
        textInputLayout.error = message
        textInputLayout.isErrorEnabled = true
    }
}
package com.akerimtay.smartwardrobe.profile.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.di.GlideApp
import com.akerimtay.smartwardrobe.common.utils.FormatHelper
import com.akerimtay.smartwardrobe.common.utils.RequestDrawableListenerAdapter
import com.akerimtay.smartwardrobe.common.utils.observeNotNull
import com.akerimtay.smartwardrobe.common.utils.setThrottleOnClickListener
import com.akerimtay.smartwardrobe.common.utils.showErrorMessage
import com.akerimtay.smartwardrobe.common.utils.showToast
import com.akerimtay.smartwardrobe.databinding.FragmentProfileBinding
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private val binding: FragmentProfileBinding by viewBinding()
    private val viewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
            )
            swipeRefreshLayout.setOnRefreshListener { viewModel.loadUser() }
            settingsImageButton.setThrottleOnClickListener { showToast(R.string.on_dev) }
            editProfileCardView.setThrottleOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment())
            }
            favoritesCardView.setThrottleOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToFavoritesFragment())
            }
            aboutAppCardView.setThrottleOnClickListener { showToast(R.string.on_dev) }
            contactUsCardView.setThrottleOnClickListener { showToast(R.string.on_dev) }
            logOutCardView.setThrottleOnClickListener {
                MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialogStyle)
                    .setTitle(R.string.logout_message_title)
                    .setMessage(R.string.logout_message_description)
                    .setNegativeButton(R.string.no) { dialog: DialogInterface, _: Int -> dialog.cancel() }
                    .setPositiveButton(R.string.log_out) { dialog: DialogInterface, _: Int ->
                        dialog.cancel()
                        viewModel.logOut()
                    }
                    .create()
                    .show()
            }
        }

        viewModel.swipeRefreshLoading.observeNotNull(viewLifecycleOwner) {
            binding.swipeRefreshLayout.isRefreshing = it
        }
        viewModel.progressLoading.observeNotNull(viewLifecycleOwner) { binding.progressStateView.isVisible = it }
        viewModel.actions.observeNotNull(viewLifecycleOwner) { action ->
            when (action) {
                is ProfileAction.ShowLoginScreen -> {
                    val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    navController.navigate(R.id.action_mainFragment_to_authFlow)
                }
                is ProfileAction.ShowErrorMessage -> showErrorMessage(action.errorMessage)
            }
        }
        viewModel.currentUser.observeNotNull(viewLifecycleOwner) { user ->
            user?.let {
                GlideApp.with(this)
                    .load(it.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .fitCenter()
                    .thumbnail(0.3f)
                    .listener(
                        RequestDrawableListenerAdapter(
                            onFailed = { binding.progressBar.isVisible = false },
                            onReady = { binding.progressBar.isVisible = false }
                        )
                    )
                    .placeholder(R.drawable.placeholder_person)
                    .into(binding.avatarImageView)
                binding.nameTextView.text = it.name
                binding.emailTextView.text = it.email
                binding.birthDateTextView.text = FormatHelper.getDate(it.birthDate)
                binding.birthDateTextView.isVisible = it.birthDate != null
                binding.genderTextView.text = getString(it.gender.displayName)
            }
        }
    }
}
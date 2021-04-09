package com.akerimtay.smartwardrobe.profile.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.utils.*
import com.akerimtay.smartwardrobe.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private val binding: FragmentProfileBinding by viewBinding()
    private val viewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            settingsImageButton.setThrottleOnClickListener { showToast(R.string.on_dev) }
            editProfileCardView.setThrottleOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment())
            }
            favoritesCardView.setThrottleOnClickListener { showToast(R.string.on_dev) }
            aboutAppCardView.setThrottleOnClickListener { showToast(R.string.on_dev) }
            contactUsCardView.setThrottleOnClickListener { showToast(R.string.on_dev) }
            logOutCardView.setThrottleOnClickListener { viewModel.logOut() }
        }

        viewModel.progressLoading.observeNotNull(viewLifecycleOwner) { binding.progressStateView.isVisible = it }
        viewModel.actions.observeNotNull(viewLifecycleOwner) { action ->
            when (action) {
                ProfileAction.ShowLoginScreen -> {
                    val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    navController.navigate(R.id.action_mainFragment_to_authFlow)
                }
            }
        }
        viewModel.currentUser.observeNotNull(viewLifecycleOwner) { user ->
            user?.let {
                binding.avatarImageView.loadImage(it.imageUrl)
                binding.nameTextView.text = it.name
                binding.emailTextView.text = it.email
                binding.birthDateTextView.text = FormatHelper.getDate(it.birthDate)
                binding.birthDateTextView.isVisible = it.birthDate != null
                binding.genderTextView.text = getString(it.gender.displayName)
            }
        }
    }
}
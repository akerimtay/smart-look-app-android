package com.akerimtay.smartwardrobe.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.utils.FormatHelper
import com.akerimtay.smartwardrobe.common.utils.loadImage
import com.akerimtay.smartwardrobe.common.utils.observeNotNull
import com.akerimtay.smartwardrobe.common.utils.setThrottleOnClickListener
import com.akerimtay.smartwardrobe.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private val binding: FragmentProfileBinding by viewBinding()
    private val viewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logOutButton.setThrottleOnClickListener { viewModel.logOut() }

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
            if (user != null) {
                binding.avatarImageView.loadImage(user.imageUrl)
                binding.nameTextView.text = user.name
                binding.emailTextView.text = user.email
                binding.birthDateTextView.text = FormatHelper.getDate(user.birthDate)
                binding.genderTextView.text = getString(user.gender.displayName)
            }
        }
    }
}
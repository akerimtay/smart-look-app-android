package com.akerimtay.smartwardrobe.feed

import android.Manifest
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.persistence.PreferencesContract
import com.akerimtay.smartwardrobe.common.utils.isPermissionsGranted
import com.akerimtay.smartwardrobe.common.utils.shouldShowRequestPermissionsRationale
import com.akerimtay.smartwardrobe.databinding.FragmentFeedBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

private val LOCATION_PERMISSIONS = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)

class FeedFragment : BaseFragment(R.layout.fragment_feed) {
    private val binding: FragmentFeedBinding by viewBinding()
    private val viewModel: FeedViewModel by viewModel()
    private val preferences: PreferencesContract by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.weather.observe(viewLifecycleOwner) {
            binding.weather.text = it.toString()
        }

        when {
            isPermissionsGranted(requireContext(), LOCATION_PERMISSIONS) -> viewModel.loadWeather(
                longitude = 71.4459800f,
                latitude = 51.1801000f,
            )
            shouldShowRequestPermissionsRationale(LOCATION_PERMISSIONS) -> {
                if (preferences.longitude != 0f && preferences.latitude != 0f) {
                    viewModel.loadWeather(
                        longitude = preferences.longitude,
                        latitude = preferences.latitude,
                    )
                } else {
                    MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialogStyle)
                        .setTitle(R.string.permission_denied_title)
                        .setMessage(R.string.permission_denied_message)
                        .setNegativeButton(R.string.no_thanks) { dialog: DialogInterface, _: Int -> dialog.cancel() }
                        .setPositiveButton(R.string.okey) { dialog: DialogInterface, _: Int ->
                            dialog.cancel()
                            locationPermissionLauncher.launch(LOCATION_PERMISSIONS)
                        }
                        .create()
                        .show()
                }
            }
            else -> {
                locationPermissionLauncher.launch(LOCATION_PERMISSIONS)
            }
        }
    }

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                if (it.value == false && !shouldShowRequestPermissionRationale(it.key)) {
                    // TODO show snackbar
                    Timber.e("show dialog don't ask again")
                }
            }
        }
}
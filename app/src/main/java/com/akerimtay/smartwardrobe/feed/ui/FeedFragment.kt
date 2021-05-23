package com.akerimtay.smartwardrobe.feed.ui

import android.Manifest
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.base.adapter.PagedContentAdapter
import com.akerimtay.smartwardrobe.common.di.GlideApp
import com.akerimtay.smartwardrobe.common.persistence.PreferencesContract
import com.akerimtay.smartwardrobe.common.ui.DefaultItemDecorator
import com.akerimtay.smartwardrobe.common.utils.action
import com.akerimtay.smartwardrobe.common.utils.dip
import com.akerimtay.smartwardrobe.common.utils.getSettingsIntent
import com.akerimtay.smartwardrobe.common.utils.isNull
import com.akerimtay.smartwardrobe.common.utils.isPermissionsGranted
import com.akerimtay.smartwardrobe.common.utils.isPositive
import com.akerimtay.smartwardrobe.common.utils.observeNotNull
import com.akerimtay.smartwardrobe.common.utils.shouldShowRequestPermissionsRationale
import com.akerimtay.smartwardrobe.common.utils.showToast
import com.akerimtay.smartwardrobe.common.utils.snack
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.content.LoadStateAdapter
import com.akerimtay.smartwardrobe.databinding.FragmentFeedBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.math.roundToInt
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private val LOCATION_PERMISSIONS = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)

private const val DIVIDER_SIZE = 16
private const val VERTICAL_SIZE = 8
private const val HORIZONTAL_SIZE = 4

class FeedFragment : BaseFragment(R.layout.fragment_feed) {
    private val binding: FragmentFeedBinding by viewBinding()
    private val viewModel: FeedViewModel by viewModel()
    private val preferences: PreferencesContract by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val glide = GlideApp.with(this)
        val contentAdapter = get<PagedContentAdapter<ItemContentType>> { parametersOf(glide) }
        with(contentAdapter) {
            addLoadStateListener { loadState ->
                binding.emptyStateView.isVisible = loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        itemCount < 1
                binding.swipeRefreshLayout.isRefreshing = loadState.refresh is LoadState.Loading
            }
            withLoadStateHeaderAndFooter(
                header = LoadStateAdapter(this),
                footer = LoadStateAdapter(this)
            )
        }
        with(binding.recyclerView) {
            adapter = contentAdapter
            itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
            setHasFixedSize(true)
            addItemDecoration(
                DefaultItemDecorator(
                    right = dip(HORIZONTAL_SIZE),
                    left = dip(HORIZONTAL_SIZE),
                    bottom = dip(VERTICAL_SIZE),
                    divider = dip(DIVIDER_SIZE),
                )
            )
        }
        with(binding.swipeRefreshLayout) {
            setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
            )
            setOnRefreshListener { contentAdapter.refresh() }
        }

        viewModel.weather.observe(viewLifecycleOwner) { weather ->
            binding.weatherView.isVisible = weather.isNull().not()
            if (weather != null) {
                binding.cityNameTextView.text = getString(
                    R.string.city_name_format,
                    weather.cityName,
                    weather.countryCode.orEmpty()
                )
                binding.iconImageView.setImageResource(weather.iconResId)
                binding.temperatureTextView.text = formatTemperature(weather.temperature)
                binding.descriptionTextView.text = weather.description.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                }
                binding.feelsLikeValueTextView.text = formatTemperature(weather.feelsLike)
            }
        }
        viewModel.actions.observeNotNull(viewLifecycleOwner) { action ->
            when (action) {
                is FeedAction.ShowMessage -> showToast(messageResId = action.messageResId)
            }
        }
        viewModel.outfits.observeNotNull(viewLifecycleOwner) { pagingData ->
            contentAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            binding.emptyStateView.isVisible = false
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

    private fun formatTemperature(value: Double): String {
        val resId =
            if (value.isPositive()) R.string.positive_temperature_format else R.string.negative_temperature_format
        return getString(resId, value.roundToInt())
    }

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val isGranted = permissions.entries.map { it.value }.contains(element = false).not()
            when {
                isGranted -> viewModel.loadWeather(
                    longitude = 71.4459800f,
                    latitude = 51.1801000f,
                )
                !isGranted && !shouldShowRequestPermissionsRationale(LOCATION_PERMISSIONS) -> {
                    binding.content.snack(R.string.permission_denied_title) {
                        action(R.string.settings) {
                            settingsLauncher.launch(requireContext().getSettingsIntent())
                        }
                    }
                }
            }
        }

    private val settingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (isPermissionsGranted(requireContext(), LOCATION_PERMISSIONS)) {
                viewModel.loadWeather(
                    longitude = 71.4459800f,
                    latitude = 51.1801000f,
                )
            }
        }
}
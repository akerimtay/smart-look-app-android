package com.akerimtay.smartwardrobe.favorites.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.base.adapter.ContentAdapter
import com.akerimtay.smartwardrobe.common.di.GlideApp
import com.akerimtay.smartwardrobe.common.ui.DefaultItemDecorator
import com.akerimtay.smartwardrobe.common.utils.dip
import com.akerimtay.smartwardrobe.common.utils.observeNotNull
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.databinding.FragmentFavoritesBinding
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val DIVIDER_SIZE = 16
private const val VERTICAL_SIZE = 8
private const val HORIZONTAL_SIZE = 4

class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {
    private val binding: FragmentFavoritesBinding by viewBinding()
    private val viewModel: FavoritesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        val glide = GlideApp.with(this)
        val contentAdapter = get<ContentAdapter<ItemContentType>> { parametersOf(glide) }

        with(binding.recyclerView) {
            adapter = contentAdapter
            itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
            setHasFixedSize(true)
            addItemDecoration(
                DefaultItemDecorator(
                    right = dip(HORIZONTAL_SIZE),
                    left = dip(HORIZONTAL_SIZE),
                    bottom = dip(VERTICAL_SIZE),
                    divider = dip(DIVIDER_SIZE)
                )
            )
        }

        viewModel.favorites.observeNotNull(viewLifecycleOwner) {
            contentAdapter.collection = it
            binding.emptyStateView.isVisible = it.isEmpty()
        }
        viewModel.actions.observeNotNull(viewLifecycleOwner) { action ->
            when (action) {
                is FavoritesAction.ShowOutfitDetailScreen -> {
                }
            }
        }
    }
}
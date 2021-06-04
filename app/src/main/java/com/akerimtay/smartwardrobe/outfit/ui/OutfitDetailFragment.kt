package com.akerimtay.smartwardrobe.outfit.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.base.adapter.ContentAdapter
import com.akerimtay.smartwardrobe.common.di.GlideApp
import com.akerimtay.smartwardrobe.common.ui.DefaultItemDecorator
import com.akerimtay.smartwardrobe.common.utils.RequestDrawableListenerAdapter
import com.akerimtay.smartwardrobe.common.utils.dip
import com.akerimtay.smartwardrobe.common.utils.observeNotNull
import com.akerimtay.smartwardrobe.common.utils.showToast
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.databinding.FragmentOutfitDetailBinding
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val DIVIDER_SIZE = 8
private const val HORIZONTAL_SIZE = 16

class OutfitDetailFragment : BaseFragment(R.layout.fragment_outfit_detail) {
    private val binding: FragmentOutfitDetailBinding by viewBinding()
    private val args: OutfitDetailFragmentArgs by navArgs()
    private val viewModel: OutfitDetailViewModel by viewModel { parametersOf(args.outfitId) }

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
                    divider = dip(DIVIDER_SIZE),
                    orientation = RecyclerView.HORIZONTAL
                )
            )
        }

        viewModel.outfit.observeNotNull(viewLifecycleOwner) { outfit ->
            outfit?.let {
                binding.toolbar.title = it.name
                glide.load(it.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .fitCenter()
                    .thumbnail(0.3f)
                    .listener(
                        RequestDrawableListenerAdapter(
                            onFailed = { binding.imageProgressBar.isVisible = false },
                            onReady = { binding.imageProgressBar.isVisible = false }
                        )
                    )
                    .placeholder(R.drawable.placeholder)
                    .into(binding.pictureImageView)
                binding.nameTextView.text = it.name
                binding.genderTextView.text = getString(it.gender.titleResId)
                binding.seasonTextView.text = getString(it.season.titleResId)
            }
        }
        viewModel.similarOutfits.observeNotNull(viewLifecycleOwner) {
            contentAdapter.collection = it
            binding.similarTextView.isVisible = it.isNotEmpty()
        }
        viewModel.actions.observeNotNull(viewLifecycleOwner) { action ->
            when (action) {
                is OutfitDetailAction.ShowSimilarOutfit -> findNavController().navigate(
                    OutfitDetailFragmentDirections.actionOutfitDetailFragmentSelf(action.outfitId)
                )
                is OutfitDetailAction.ShowMessage -> showToast(messageResId = action.messageResId)
            }
        }
        viewModel.isFavoriteOutfit.observeNotNull(viewLifecycleOwner) {
            binding.favoriteCheckBox.isChecked = it
            binding.favoriteCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) viewModel.addFavorite() else viewModel.deleteFavorite()
            }
        }
        viewModel.favoriteProgressLoading.observeNotNull(viewLifecycleOwner) {
            binding.favoriteCheckBox.isVisible = !it
            binding.favoriteProgressBar.isVisible = it
        }
    }
}
package com.akerimtay.smartwardrobe.outfit.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.di.GlideApp
import com.akerimtay.smartwardrobe.common.utils.RequestDrawableListenerAdapter
import com.akerimtay.smartwardrobe.common.utils.observeNotNull
import com.akerimtay.smartwardrobe.common.utils.toast
import com.akerimtay.smartwardrobe.databinding.FragmentOutfitDetailBinding
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class OutfitDetailFragment : BaseFragment(R.layout.fragment_outfit_detail) {
    private val binding: FragmentOutfitDetailBinding by viewBinding()
    private val args: OutfitDetailFragmentArgs by navArgs()
    private val viewModel: OutfitDetailViewModel by viewModel { parametersOf(args.outfitId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoriteCheckBox.setOnCheckedChangeListener { _, isChecked ->
            context?.toast(isChecked.toString())
        }

        viewModel.outfit.observeNotNull(viewLifecycleOwner) { outfit ->
            outfit?.let {
                binding.toolbar.title = it.name
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
                    .placeholder(R.drawable.placeholder)
                    .into(binding.pictureImageView)
                binding.nameTextView.text = it.name
                binding.genderTextView.text = getString(it.gender.titleResId)
                binding.seasonTextView.text = getString(it.season.titleResId)
                binding.favoriteCheckBox.isChecked = false
            }
        }
    }
}
package com.akerimtay.smartwardrobe.articles.ui

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.utils.observeNotNull
import com.akerimtay.smartwardrobe.databinding.FragmentArticlesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticlesFragment : BaseFragment(R.layout.fragment_articles) {
    private val binding: FragmentArticlesBinding by viewBinding()
    private val viewModel: ArticlesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
            )
            swipeRefreshLayout.setOnRefreshListener { viewModel.loadArticles() }
        }

        viewModel.articles.observeNotNull(viewLifecycleOwner) {

        }
        viewModel.swipeRefreshLoading.observeNotNull(viewLifecycleOwner) {
            binding.swipeRefreshLayout.isRefreshing = it
        }
    }
}
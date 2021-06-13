package com.akerimtay.smartwardrobe.articles.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.articles.ui.detail.ArticleDetailFragmentArgs
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.base.adapter.ContentAdapter
import com.akerimtay.smartwardrobe.common.di.GlideApp
import com.akerimtay.smartwardrobe.common.ui.DefaultItemDecorator
import com.akerimtay.smartwardrobe.common.utils.dip
import com.akerimtay.smartwardrobe.common.utils.observeNotNull
import com.akerimtay.smartwardrobe.common.utils.showErrorMessage
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.databinding.FragmentArticlesBinding
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val DIVIDER_SIZE = 8
private const val VERTICAL_SIZE = 16
private const val HORIZONTAL_SIZE = 16

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
                    top = dip(VERTICAL_SIZE),
                    divider = dip(DIVIDER_SIZE),
                )
            )
        }

        viewModel.articles.observeNotNull(viewLifecycleOwner) {
            contentAdapter.collection = it
            binding.emptyStateView.isVisible = it.isEmpty()
        }
        viewModel.swipeRefreshLoading.observeNotNull(viewLifecycleOwner) {
            binding.swipeRefreshLayout.isRefreshing = it
        }
        viewModel.actions.observeNotNull(viewLifecycleOwner) { action ->
            when (action) {
                is ArticlesAction.ShowDetailScreen -> {
                    val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    navController.navigate(
                        R.id.action_mainFragment_to_articleDetailFlow,
                        ArticleDetailFragmentArgs(articleId = action.articleId).toBundle()
                    )
                }
                is ArticlesAction.ShowErrorMessage -> showErrorMessage(action.errorMessage)
            }
        }
    }
}
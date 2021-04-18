package com.akerimtay.smartwardrobe.feed.ui.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.base.adapter.PagedContentAdapter
import com.akerimtay.smartwardrobe.common.di.GlideApp
import com.akerimtay.smartwardrobe.common.ui.DefaultItemDecorator
import com.akerimtay.smartwardrobe.common.utils.args
import com.akerimtay.smartwardrobe.common.utils.dip
import com.akerimtay.smartwardrobe.common.utils.observeNotNull
import com.akerimtay.smartwardrobe.common.utils.withArgs
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.content.LoadStateAdapter
import com.akerimtay.smartwardrobe.databinding.FragmentFeedListBinding
import com.akerimtay.smartwardrobe.user.model.Gender
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val GENDER_EXTRA = "GENDER_EXTRA"
private const val DIVIDER_SIZE = 16
private const val VERTICAL_SIZE = 8
private const val HORIZONTAL_SIZE = 4

class FeedListFragment : BaseFragment(R.layout.fragment_feed_list) {
    companion object {
        fun create(gender: Gender) = FeedListFragment().withArgs(GENDER_EXTRA to gender)
    }

    private val binding: FragmentFeedListBinding by viewBinding()
    private val gender: Gender by args(GENDER_EXTRA)
    private val viewModel: FeedListViewModel by viewModel { parametersOf(gender) }

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

        viewModel.outfits.observeNotNull(viewLifecycleOwner) { pagingData ->
            contentAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            binding.emptyStateView.isVisible = false
        }
    }
}
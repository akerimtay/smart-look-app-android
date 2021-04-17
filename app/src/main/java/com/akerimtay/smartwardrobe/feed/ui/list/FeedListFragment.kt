package com.akerimtay.smartwardrobe.feed.ui.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.base.adapter.PagedContentAdapter
import com.akerimtay.smartwardrobe.common.utils.args
import com.akerimtay.smartwardrobe.common.utils.observeNotNull
import com.akerimtay.smartwardrobe.common.utils.withArgs
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.databinding.FragmentFeedListBinding
import com.akerimtay.smartwardrobe.user.model.Gender
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val GENDER_EXTRA = "GENDER_EXTRA"

class FeedListFragment : BaseFragment(R.layout.fragment_feed_list) {
    companion object {
        fun create(gender: Gender) = FeedListFragment().withArgs(GENDER_EXTRA to gender)
    }

    private val binding: FragmentFeedListBinding by viewBinding()
    private val gender: Gender by args(GENDER_EXTRA)
    private val viewModel: FeedListViewModel by viewModel { parametersOf(gender) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contentAdapter = get<PagedContentAdapter<ItemContentType>>()
        with(binding) {
            recyclerView.adapter = contentAdapter
        }

        viewModel.outfits.observeNotNull(viewLifecycleOwner) { pagingData ->
            contentAdapter.submitData(lifecycle, pagingData)
            binding.emptyStateView.isVisible = false
        }
    }
}
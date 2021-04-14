package com.akerimtay.smartwardrobe.feed.ui.list

import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.utils.args
import com.akerimtay.smartwardrobe.common.utils.withArgs
import com.akerimtay.smartwardrobe.databinding.FragmentFeedListBinding
import com.akerimtay.smartwardrobe.user.model.Gender

private const val GENDER_EXTRA = "GENDER_EXTRA"

class FeedListFragment : BaseFragment(R.layout.fragment_feed_list) {
    companion object {
        fun create(gender: Gender) = FeedListFragment().withArgs(GENDER_EXTRA to gender)
    }

    private val binding: FragmentFeedListBinding by viewBinding()
    private val gender: Gender by args(GENDER_EXTRA)
}
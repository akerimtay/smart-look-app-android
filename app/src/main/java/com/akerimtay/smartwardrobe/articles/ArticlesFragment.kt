package com.akerimtay.smartwardrobe.articles

import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.databinding.FragmentArticlesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticlesFragment : BaseFragment(R.layout.fragment_articles) {
    private val binding: FragmentArticlesBinding by viewBinding()
    private val viewModel: ArticlesViewModel by viewModel()
}
package com.akerimtay.smartwardrobe.common.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.akerimtay.smartwardrobe.common.base.BaseFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragments = mutableListOf<BaseFragment>()

    override fun createFragment(position: Int): Fragment = fragments[position]

    override fun getItemCount(): Int = fragments.size

    fun addFragments(values: List<BaseFragment>) {
        fragments.clear()
        fragments.addAll(values)
    }
}
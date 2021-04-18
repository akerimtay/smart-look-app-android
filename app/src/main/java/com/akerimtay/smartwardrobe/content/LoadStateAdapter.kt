package com.akerimtay.smartwardrobe.content

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.akerimtay.smartwardrobe.common.base.adapter.PagedContentAdapter
import com.akerimtay.smartwardrobe.content.viewholder.NetworkStateViewHolder

class LoadStateAdapter(
    private val adapter: PagedContentAdapter<ItemContentType>,
) : LoadStateAdapter<NetworkStateViewHolder>() {
    override fun onBindViewHolder(holder: NetworkStateViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): NetworkStateViewHolder =
        NetworkStateViewHolder(parent) { adapter.retry() }
}
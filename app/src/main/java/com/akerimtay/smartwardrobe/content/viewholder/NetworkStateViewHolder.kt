package com.akerimtay.smartwardrobe.content.viewholder

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.recyclerview.widget.RecyclerView
import com.akerimtay.smartwardrobe.common.utils.setThrottleOnClickListener
import kotlinx.android.synthetic.main.item_network_state.view.*

class NetworkStateViewHolder(
    containerView: View,
    private val retryCallback: () -> Unit,
) : RecyclerView.ViewHolder(containerView) {
    init {
        itemView.retry_button.setThrottleOnClickListener { retryCallback() }
    }

    fun bindTo(loadState: LoadState) {
        with(itemView) {
            progress_bar.isVisible = loadState is Loading
            retry_button.isVisible = loadState is Error
            error_text_view.isVisible = !(loadState as? Error)?.error?.message.isNullOrBlank()
            error_text_view.text = (loadState as? Error)?.error?.message
        }
    }
}
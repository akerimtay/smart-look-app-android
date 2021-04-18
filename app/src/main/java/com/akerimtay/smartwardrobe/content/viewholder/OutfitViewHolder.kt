package com.akerimtay.smartwardrobe.content.viewholder

import android.view.View
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.adapter.BaseHolder
import com.akerimtay.smartwardrobe.common.di.GlideRequests
import com.akerimtay.smartwardrobe.common.utils.load
import com.akerimtay.smartwardrobe.common.utils.setThrottleOnClickListener
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.content.item.OutfitItem
import kotlinx.android.synthetic.main.item_outfit.view.*

class OutfitViewHolder(
    containerView: View,
    private val glide: GlideRequests?,
) : BaseHolder<ItemContentType, OutfitItem>(containerView) {
    init {
        itemView.setThrottleOnClickListener { getItem()?.onItemClickListener?.invoke() }
    }

    override fun bindItem(item: OutfitItem) {
        glide?.let {
            itemView.image_view.load(
                glide = it,
                imageUrl = item.outfit.imageUrl,
                placeholder = R.drawable.placeholder
            )
        }
    }
}
package com.akerimtay.smartwardrobe.content.viewholder

import android.view.View
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
        itemView.image_view.load(
            glide = glide,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/smartwardrobe-535fe.appspot.com/o/images%2Fusers%2Fe874c3e4-5b9d-4986-bf8d-a138470b6fa4?alt=media&token=379530c6-07ff-4b53-b993-b77dc9e35f0b",
        )
    }
}
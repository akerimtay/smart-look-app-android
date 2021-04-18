package com.akerimtay.smartwardrobe.content.viewholder

import android.view.View
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.adapter.BaseHolder
import com.akerimtay.smartwardrobe.common.di.GlideRequests
import com.akerimtay.smartwardrobe.common.utils.dip
import com.akerimtay.smartwardrobe.common.utils.setThrottleOnClickListener
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.content.item.OutfitItem
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_outfit.view.*

class OutfitViewHolder(
    containerView: View,
    private val glide: GlideRequests?,
) : BaseHolder<ItemContentType, OutfitItem>(containerView) {
    init {
        itemView.setThrottleOnClickListener { getItem()?.onItemClickListener?.invoke() }
    }

    override fun bindItem(item: OutfitItem) {
        with(itemView) {
            glide?.load(item.outfit.imageUrl)?.diskCacheStrategy(DiskCacheStrategy.ALL)?.centerCrop()?.fitCenter()
                ?.thumbnail(0.3f)?.override(dip(200))?.placeholder(R.drawable.placeholder)?.into(image_view)
        }
    }
}
package com.akerimtay.smartwardrobe.content.viewholder

import android.view.View
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.adapter.BaseHolder
import com.akerimtay.smartwardrobe.common.di.GlideRequests
import com.akerimtay.smartwardrobe.common.utils.setThrottleOnClickListener
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.content.item.OutfitDetailItem
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_outfit_detail.view.*

class OutfitDetailViewHolder(
    containerView: View,
    private val glide: GlideRequests?,
) : BaseHolder<ItemContentType, OutfitDetailItem>(containerView) {
    init {
        itemView.setThrottleOnClickListener { getItem()?.onItemClickListener?.invoke() }
    }

    override fun bindItem(item: OutfitDetailItem) {
        with(itemView) {
            glide?.load(item.outfit.imageUrl)?.diskCacheStrategy(DiskCacheStrategy.ALL)?.centerCrop()
                ?.thumbnail(0.3f)?.placeholder(R.drawable.placeholder)?.into(image_view)
        }
    }
}
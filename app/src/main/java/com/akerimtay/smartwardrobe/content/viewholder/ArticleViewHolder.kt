package com.akerimtay.smartwardrobe.content.viewholder

import android.view.View
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.adapter.BaseHolder
import com.akerimtay.smartwardrobe.common.di.GlideRequests
import com.akerimtay.smartwardrobe.common.utils.setThrottleOnClickListener
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.content.item.ArticleItem
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_article.view.*

class ArticleViewHolder(
    containerView: View,
    private val glide: GlideRequests?,
) : BaseHolder<ItemContentType, ArticleItem>(containerView) {
    init {
        itemView.setThrottleOnClickListener { getItem()?.onItemClickListener?.invoke() }
    }

    override fun bindItem(item: ArticleItem) {
        with(itemView) {
            glide
                ?.load(item.article.imageUrl)
                ?.diskCacheStrategy(DiskCacheStrategy.ALL)
                ?.centerCrop()
                ?.fitCenter()
                ?.thumbnail(0.3f)
                ?.placeholder(R.drawable.placeholder)
                ?.into(image_view)
            name_text_view.text = item.article.name
            description_text_view.text = item.article.description
        }
    }
}
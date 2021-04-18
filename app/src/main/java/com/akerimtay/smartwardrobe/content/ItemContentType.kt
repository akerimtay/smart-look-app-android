package com.akerimtay.smartwardrobe.content

import android.view.View
import androidx.annotation.Keep
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.adapter.ContentType
import com.akerimtay.smartwardrobe.common.di.GlideRequests
import com.akerimtay.smartwardrobe.content.viewholder.ActionMenuItemViewHolder
import com.akerimtay.smartwardrobe.content.viewholder.ArticleViewHolder
import com.akerimtay.smartwardrobe.content.viewholder.OutfitViewHolder

@Keep
enum class ItemContentType : ContentType {
    ACTION_MENU {
        override fun type(): Int = ordinal
        override fun getLayout(): Int = R.layout.item_action_menu
        override fun createHolder(view: View, glide: GlideRequests?) = ActionMenuItemViewHolder(view)
    },
    OUTFIT {
        override fun type(): Int = ordinal
        override fun getLayout(): Int = R.layout.item_outfit
        override fun createHolder(view: View, glide: GlideRequests?) = OutfitViewHolder(view, glide)
    },
    ARTICLE {
        override fun type(): Int = ordinal
        override fun getLayout(): Int = R.layout.item_article
        override fun createHolder(view: View, glide: GlideRequests?) = ArticleViewHolder(view, glide)
    },
}
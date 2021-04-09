package com.akerimtay.smartwardrobe.content

import android.view.View
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.adapter.ContentType
import com.akerimtay.smartwardrobe.content.viewholder.ActionMenuItemViewHolder

enum class ItemContentType : ContentType {
    ACTION_MENU {
        override fun type(): Int = ordinal
        override fun getLayout(): Int = R.layout.item_action_menu
        override fun createHolder(view: View) = ActionMenuItemViewHolder(view)
    }
}
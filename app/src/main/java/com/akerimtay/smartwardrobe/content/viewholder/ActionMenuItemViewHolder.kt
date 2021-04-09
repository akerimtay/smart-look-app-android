package com.akerimtay.smartwardrobe.content.viewholder

import android.view.View
import com.akerimtay.smartwardrobe.common.base.adapter.BaseHolder
import com.akerimtay.smartwardrobe.common.utils.setThrottleOnClickListener
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.content.item.ActionMenuItem
import kotlinx.android.synthetic.main.item_action_menu.*

class ActionMenuItemViewHolder(containerView: View) : BaseHolder<ItemContentType, ActionMenuItem>(containerView) {
    init {
        itemView.setThrottleOnClickListener { getItem()?.onItemClickListener?.invoke() }
    }

    override fun bindItem(item: ActionMenuItem) {
        with(itemView.context) {
            action_title.text = getString(item.actionMenuType.titleResId)
            action_image_view.setImageResource(item.actionMenuType.iconResId)
        }
    }
}
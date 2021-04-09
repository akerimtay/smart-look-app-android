package com.akerimtay.smartwardrobe.content.item

import com.akerimtay.smartwardrobe.common.base.adapter.BaseContentItem
import com.akerimtay.smartwardrobe.common.model.ActionMenuType
import com.akerimtay.smartwardrobe.content.model.ItemContentType

data class ActionMenuItem(
    val actionMenuType: ActionMenuType,
    val onItemClickListener: () -> Unit
) : BaseContentItem<ItemContentType>(actionMenuType.ordinal.toString()) {
    override val type: ItemContentType = ItemContentType.ACTION_MENU
}
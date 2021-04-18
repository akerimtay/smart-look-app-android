package com.akerimtay.smartwardrobe.content.item

import com.akerimtay.smartwardrobe.common.base.adapter.BaseContentItem
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.outfit.model.Outfit

data class OutfitItem(
    val outfit: Outfit,
    val onItemClickListener: () -> Unit,
) : BaseContentItem<ItemContentType>(outfit.id.toString()) {
    override val type: ItemContentType = ItemContentType.OUTFIT
}
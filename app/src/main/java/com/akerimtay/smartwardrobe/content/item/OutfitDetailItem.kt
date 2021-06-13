package com.akerimtay.smartwardrobe.content.item

import com.akerimtay.smartwardrobe.common.base.adapter.BaseContentItem
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.outfit.model.Outfit

class OutfitDetailItem(
    val outfit: Outfit,
    val onItemClickListener: () -> Unit,
) : BaseContentItem<ItemContentType>(outfit.id.toString()) {
    override val type: ItemContentType = ItemContentType.OUTFIT_DETAIL
}
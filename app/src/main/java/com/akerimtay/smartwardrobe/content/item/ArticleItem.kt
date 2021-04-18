package com.akerimtay.smartwardrobe.content.item

import com.akerimtay.smartwardrobe.articles.model.Article
import com.akerimtay.smartwardrobe.common.base.adapter.BaseContentItem
import com.akerimtay.smartwardrobe.content.ItemContentType

data class ArticleItem(
    val article: Article,
    val onItemClickListener: () -> Unit,
) : BaseContentItem<ItemContentType>(article.id.toString()) {
    override val type: ItemContentType = ItemContentType.ARTICLE
}
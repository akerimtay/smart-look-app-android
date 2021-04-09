package com.akerimtay.smartwardrobe.common.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseHolder<C : ContentType, T : BaseContentItem<C>>(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    private var homeItem: BaseContentItem<*>? = null

    fun bind(item: BaseContentItem<*>) {
        homeItem = item
        (homeItem as? T)?.let { bindItem(it) }
    }

    fun getItem() = (homeItem as? T)

    open fun getNestedRecyclerView(): RecyclerView? = null

    abstract fun bindItem(item: T)

    fun updateHolder(item: BaseContentItem<*>, keys: Set<String>) {
        homeItem = item
        keys.forEach { update(it, item as T) }
    }

    open fun update(key: String, item: T) {}

    open fun onViewRecycled() {}

    open fun onViewAttachedToWindow() {}

    open fun onViewDetachedFromWindow() {}
}
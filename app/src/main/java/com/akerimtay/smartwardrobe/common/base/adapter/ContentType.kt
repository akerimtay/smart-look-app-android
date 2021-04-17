package com.akerimtay.smartwardrobe.common.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.akerimtay.smartwardrobe.common.di.GlideRequests

interface ContentType {
    fun type(): Int
    fun getLayout(): Int
    fun createHolder(view: View, glide: GlideRequests?): RecyclerView.ViewHolder
}
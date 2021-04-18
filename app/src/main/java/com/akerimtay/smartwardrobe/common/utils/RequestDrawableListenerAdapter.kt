package com.akerimtay.smartwardrobe.common.utils

import android.graphics.drawable.Drawable
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class RequestDrawableListenerAdapter(
    private val onFailed: (() -> Unit)? = null,
    private val onReady: (() -> Unit)? = null,
) : RequestListener<Drawable> {

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean,
    ): Boolean {
        onFailed?.let { it() }
        return false
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean,
    ): Boolean {
        onReady?.let { it() }
        return false
    }
}
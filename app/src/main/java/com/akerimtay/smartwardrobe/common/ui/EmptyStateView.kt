package com.akerimtay.smartwardrobe.common.ui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import com.akerimtay.smartwardrobe.R
import kotlinx.android.synthetic.main.view_empty_state.view.*

class EmptyStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    var title: String
        get() = titleTextView.text.toString()
        set(value) {
            titleTextView.text = value
        }

    var description: String
        get() = descriptionTextView.text.toString()
        set(value) {
            descriptionTextView.text = value
        }

    init {
        inflate(context, R.layout.view_empty_state, this)

        val attributes = context.obtainStyledAttributes(
            attrs,
            R.styleable.EmptyStateView,
            defStyleAttr,
            0
        )
        titleTextView.text = attributes.getString(R.styleable.EmptyStateView_title).orEmpty()
        descriptionTextView.text = attributes.getString(R.styleable.EmptyStateView_description).orEmpty()
        val isBlackStyle = attributes.getBoolean(R.styleable.EmptyStateView_isBlackStyle, true)
        if (!isBlackStyle) {
            coverImageView.setImageResource(R.drawable.ic_empty_list)
            titleTextView.setTextColor(Color.BLACK)
            descriptionTextView.setTextColor(Color.BLACK)
        }
        attributes.recycle()
    }
}
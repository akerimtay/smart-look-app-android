package com.akerimtay.smartwardrobe.common.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.akerimtay.smartwardrobe.R
import kotlinx.android.synthetic.main.view_progress_state.view.*

class ProgressStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    var text: String
        get() = textTextView.text.toString()
        set(value) {
            textTextView.text = value
        }

    init {
        inflate(context, R.layout.view_progress_state, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ProgressStateView, defStyleAttr, 0)
        textTextView.text = attributes.getString(R.styleable.ProgressStateView_text).orEmpty()
        attributes.recycle()
    }
}
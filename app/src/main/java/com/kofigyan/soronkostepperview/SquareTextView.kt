package com.kofigyan.soronkostepperview

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class SquareTextView(context: Context, attrs: AttributeSet?, defStyleAttrs: Int) :
    TextView(context, attrs, defStyleAttrs) {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val r = Math.max(measuredWidth, measuredHeight)
        setMeasuredDimension(r, r)
    }

}
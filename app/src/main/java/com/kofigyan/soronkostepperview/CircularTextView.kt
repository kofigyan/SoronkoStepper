package com.kofigyan.soronkostepperview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView

class CircularTextView(context: Context, attrs: AttributeSet?, defStyleAttrs: Int) :
    TextView(context, attrs, defStyleAttrs) {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)


    private var mCheckStateCompleted: Boolean = false

    private val mCheckFont: Typeface by lazy { getCustomTypeface() }

    private val mDefaultTypefaceBold: Typeface by lazy {
        Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    private var mBackgroundColor: Int = Color.GRAY
    private var mForegroundColor: Int = Color.BLUE

    private var mSpacing: Float = 0.toFloat()
    private var mStateSize: Float = 0.toFloat()
    private var mStateNumberTextSize: Float = 0.toFloat()

    private var mStrokeWidth: Float = 0.toFloat()

    //paints
    lateinit var circlePaint: Paint
    lateinit var strokePaint: Paint
    lateinit var checkPaint: Paint
    lateinit var numberPaint: Paint


    init {

        gravity = Gravity.CENTER

        mStateSize = DEFAULT_STATE_SIZE.pixelValue()

        attrs?.let {
            val typedArray: TypedArray = context.obtainStyledAttributes(it, R.styleable.CircularTextView)
            initAttrs(typedArray)
            typedArray.recycle()
        }

        initializePaints()
    }


    private fun initializePaints() {

        circlePaint = setPaintAttributes(mForegroundColor)

        strokePaint = setPaintAttributes(mForegroundColor, false) {
            strokeWidth = mStrokeWidth
        }

        numberPaint = setPaintAttributes(Color.WHITE) {
            textSize = textSize  // change to a value from user
            typeface = mDefaultTypefaceBold
            textAlign = Paint.Align.CENTER
        }

        checkPaint = setPaintAttributes(Color.WHITE) {
            typeface = mCheckFont
            textSize = textSize // change to a value from user
            textAlign = Paint.Align.CENTER
        }
    }


    private fun initAttrs(typedArray: TypedArray) {
        mBackgroundColor = typedArray.getColor(R.styleable.CircularTextView_ctv_stateBackgroundColor, mBackgroundColor)
        mForegroundColor = typedArray.getColor(R.styleable.CircularTextView_ctv_stateForegroundColor, mForegroundColor)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val diameter = mStateSize.toInt()
        val radius = diameter / 2


        //  canvas?.drawCircle(radius.toFloat(), radius.toFloat(), radius.toFloat(), strokePaint)
        canvas?.drawCircle(radius.toFloat(), radius.toFloat(), radius - mStrokeWidth, circlePaint)

        val yPos = (radius - (numberPaint.descent() + numberPaint.ascent()) / 2).toInt()

        if (!mCheckStateCompleted)
            canvas?.drawText(text.toString(), radius.toFloat(), yPos.toFloat(), numberPaint)
        else
            canvas?.drawText(
                context.getString(R.string.check_icon),
                radius.toFloat(),
                yPos.toFloat(),
                checkPaint
            )
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val height = getDesiredHeight()

        setMeasuredDimension(height, height)

    }

    private fun setPaintAttributes(color: Int, fillPaintStyle: Boolean = true, optional: Paint.() -> Unit = {}) =
        Paint().apply {
            this.color = color
            this.style = if (fillPaintStyle) Paint.Style.FILL else Paint.Style.STROKE
            this.isAntiAlias = true
            this.optional()
        }

    private fun getDesiredHeight(): Int {
        return mStateSize.toInt()
    }

    private fun getCustomTypeface() = Typeface.createFromAsset(context.assets, FONTAWESOME)

    private fun Float.pixelValue(unit: Int = TypedValue.COMPLEX_UNIT_DIP): Float {
        return TypedValue.applyDimension(unit, this, resources.displayMetrics)
    }

    companion object {
        private const val ROOTS: String = "fonts/"
        private const val FONTAWESOME = "${ROOTS}fontawesome-webfont.ttf"

        private const val DEFAULT_STATE_SIZE = 25f
    }

}
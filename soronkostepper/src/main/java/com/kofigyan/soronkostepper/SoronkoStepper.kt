package com.kofigyan.soronkostepper


import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.DrawableContainer.DrawableContainerState
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_SP
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import com.kofigyan.soronkostepper.animation.FlipAnimation3D
import com.kofigyan.soronkostepper.animation.listener.ViewFlipperListener
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty


class SoronkoStepper(context: Context, attrs: AttributeSet?, defStyleAttrs: Int) :
    LinearLayout(context, attrs, defStyleAttrs), ViewFlipperListener {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private lateinit var viewFlipperOne: ViewFlipper
    private lateinit var viewOne: SquareTextView
    private lateinit var viewOneB: SquareTextView
    private lateinit var mCurrentStepperDescription: TextView


    enum class StepperNumber(val value: Int) {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5)
    }


    var stepperBackgroundColor: Int by stepperProperty(Color.LTGRAY) {
        resetStepperColors()
    }


    var stepperForegroundColor: Int by stepperProperty(Color.rgb(165, 70, 40)) {
        resetStepperColors()
    }


    var stepperNumberBackgroundColor: Int by stepperProperty(Color.WHITE) {
        resetStepperNumberColor()
    }


    var stepperNumberForegroundColor: Int by stepperProperty(Color.WHITE) {
        resetStepperNumberColor()
    }


    var currentStepperDescriptionColor: Int by stepperProperty(Color.rgb(165, 70, 40)) {
        resetCurrentStepperDescriptionColor()
    }


    var stepperDescriptionColor: Int by stepperProperty(Color.LTGRAY) {
        resetStepperDescriptionColor()
    }


    var stepperDescriptionSize: Int by stepperProperty((DEFAULT_DESC_SIZE.pixelValue(COMPLEX_UNIT_SP)).toInt()) {
        resetStepperDescriptionSize()
    }


    var stepperNumberTextSize: Float by stepperProperty(0f) {
        resetStepperSize()
    }


    var stepperSize: Float by stepperProperty(0f) {
        resetStepperSize()
    }


    private var mMaxStepperNumber by stepperProperty(DEFAULT_MAX_STEPPER_NUMBER, true) {
        resolveMaxStepperNumberChange()
    }


    private var mCurrentStepperNumber: Int by stepperProperty(DEFAULT_CURRENT_STEPPER_NUMBER, true) {
        resolveCurrentStepperNumberChange()
    }

    private var mPreviousStepperNumber: Int = DEFAULT_PREVIOUS_STEPPER_NUMBER


    private var mIsSteppersDirty: Boolean = false


    var checkStepperCompleted: Boolean by stepperProperty(false) {
        resetCheckStepperCompleted()
    }


    var animStartDelay: Int by stepperProperty(DEFAULT_ANIM_START_DELAY)

    private var mAnimator: ViewAnimator? = null


    private val mCheckFont: Typeface by lazy { getCustomTypeface() }

    private var firstInitialized = false


    var descriptionData: Array<String> by stepperProperty(emptyArray()) {
        resetStepperDescriptionText()
    }


    var stepperNumberTypeface: String by stepperProperty(EMPTY_SPACE) {
        resetCheckStepperCompleted()
    }

    var stepperDescriptionTypeface: String by stepperProperty(EMPTY_SPACE) {
        resetStepperDescriptionTypeface()
    }


    var descriptionTruncateEnd: Boolean by stepperProperty(false) {
        updateStepperDescriptionWithEllipsize()
    }

    var descriptionMultilineTruncateEnd: Int by stepperProperty(1) {
        updateStepperDescriptionWithEllipsize(maxLines = descriptionMultilineTruncateEnd)
    }

    private val mDefaultTypefaceBold: Typeface  by lazy {
        getDefaultTypeface()
    }


    private val stepperItems: MutableList<LinearLayout> = mutableListOf()

    private val stepperItemsCache: MutableMap<LinearLayout, StepperItem> = linkedMapOf()

    private var mIsAnimationStarted: Boolean = false


    init {

        orientation = HORIZONTAL

        attrs?.let {
            val typedArray: TypedArray = context.obtainStyledAttributes(it, R.styleable.SoronkoStepper)
            initAttrs(typedArray)
            typedArray.recycle()
        }
        firstInitialized = true
    }


    private fun initAttrs(typedArray: TypedArray) {

        mMaxStepperNumber = typedArray.getInteger(R.styleable.SoronkoStepper_ssv_maxStepperNumber, mMaxStepperNumber)

        mCurrentStepperNumber =
            typedArray.getInteger(R.styleable.SoronkoStepper_ssv_currentStepperNumber, mCurrentStepperNumber)

        stepperBackgroundColor =
            typedArray.getColor(R.styleable.SoronkoStepper_ssv_stepperBackgroundColor, stepperBackgroundColor)
        stepperForegroundColor =
            typedArray.getColor(R.styleable.SoronkoStepper_ssv_stepperForegroundColor, stepperForegroundColor)

        checkStepperCompleted =
            typedArray.getBoolean(R.styleable.SoronkoStepper_ssv_checkStepperCompleted, checkStepperCompleted)

        animStartDelay = typedArray.getInteger(R.styleable.SoronkoStepper_ssv_animationStartDelay, animStartDelay)

        stepperNumberTextSize =
            typedArray.getDimension(R.styleable.SoronkoStepper_ssv_stepperTextSize, stepperNumberTextSize)

        stepperSize =
            typedArray.getDimension(R.styleable.SoronkoStepper_ssv_stepperSize, stepperSize)


        stepperDescriptionSize = typedArray.getDimensionPixelSize(
            R.styleable.SoronkoStepper_ssv_stepperDescriptionSize,
            stepperDescriptionSize
        )


        stepperNumberBackgroundColor =
            typedArray.getColor(
                R.styleable.SoronkoStepper_ssv_stepperNumberBackgroundColor,
                stepperNumberBackgroundColor
            )
        stepperNumberForegroundColor =
            typedArray.getColor(
                R.styleable.SoronkoStepper_ssv_stepperNumberForegroundColor,
                stepperNumberForegroundColor
            )
        currentStepperDescriptionColor =
            typedArray.getColor(
                R.styleable.SoronkoStepper_ssv_currentStepperDescriptionColor,
                currentStepperDescriptionColor
            )
        stepperDescriptionColor =
            typedArray.getColor(R.styleable.SoronkoStepper_ssv_stepperDescriptionColor, stepperDescriptionColor)


        resolveStepperSize()

        validateStepperNumber()

        updatePreviousStepperNumber()

        setupSteppers()

    }

    private fun checkSteppersDirty() {

        mIsSteppersDirty = mCurrentStepperNumber - mPreviousStepperNumber != 2

        if (mIsSteppersDirty) triggerSteppersDirtyAction()
    }

    private fun triggerSteppersDirtyAction() {
        stepperItemsCache.clear()
        stepperItems.clear()
    }


    private fun setupSteppers() {
        generateSteppers()
        prepareCurrentStepperViews()
        startCurrentStepperAnimation()
    }

    private fun resolveMaxStepperNumberChange() {
        validateStepperNumber()
        addSteppersToView()
    }

    private fun resolveCurrentStepperNumberChange() {

        checkSteppersDirty()

        if (mIsSteppersDirty) {
            currentNumberChangedSteppersDirtyAction()
        } else {
            currentNumberChangedSteppersNotDirtyAction()
        }
    }

    private fun currentNumberChangedSteppersNotDirtyAction() {
        validateStepperNumber()
        addSteppersToView()
        setAllSteppersAttribute(false)
        prepareCurrentStepperViews()
        startCurrentStepperAnimation()
        updatePreviousStepperNumber()
    }

    private fun updatePreviousStepperNumber() {
        mPreviousStepperNumber = mCurrentStepperNumber - 1
    }

    private fun currentNumberChangedSteppersDirtyAction() {
        setupSteppers()
        if (descriptionTruncateEnd) updateStepperDescriptionWithEllipsize()
        if (descriptionMultilineTruncateEnd > 1) updateStepperDescriptionWithEllipsize(maxLines = descriptionMultilineTruncateEnd)
        mIsSteppersDirty = false
        updatePreviousStepperNumber()
    }


    private fun validateStepperNumber() {
        if (mCurrentStepperNumber > mMaxStepperNumber) {
            throw IllegalStateException("Current Stepper $mCurrentStepperNumber cannot be greater than Max Stepper Number ${mMaxStepperNumber}")
        }
    }

    fun setMaxStepperNumber(stepperNumber: StepperNumber) {
        mMaxStepperNumber = stepperNumber.value
    }

    fun getMaxStepperNumber(): Int {
        return mMaxStepperNumber
    }

    fun setCurrentStepperNumber(stepperNumber: StepperNumber) {
        mCurrentStepperNumber = stepperNumber.value
    }

    fun getCurrentStepperNumber(): Int {
        return mCurrentStepperNumber
    }


    private fun generateSteppers() {
        for (index in 0 until DEFAULT_MAX_STEPPER_NUMBER) {
            val stepperLinearLayout: LinearLayout =
                LayoutInflater.from(context).inflate(R.layout.partial_stepper_ssv, this, false) as LinearLayout
            addStepperToView(stepperLinearLayout, index)
            stepperItems.add(stepperLinearLayout)
            val stepperItem = createStepperItem(stepperLinearLayout)
            cacheStepperItems(stepperLinearLayout, stepperItem)
            setAllSteppersAttribute(stepperItem, index, true)
        }
    }

    private fun addStepperToView(stepper: LinearLayout, index: Int) {
        if (index < mMaxStepperNumber)
            addView(stepper, index)
    }


    private fun addSteppersToView() {
        for (index in 0 until mMaxStepperNumber) {
            addView(stepperItems[index], index)
        }
    }


    private fun createStepperItem(rootLinearLayout: LinearLayout): StepperItem {
        val viewFlipper = rootLinearLayout.findViewById<ViewFlipper>(R.id.viewFlipperOne)
        val frontTextView = rootLinearLayout.findViewById<SquareTextView>(R.id.viewOne)
        val backTextView = rootLinearLayout.findViewById<SquareTextView>(R.id.viewOneB)
        val descriptionTextView = rootLinearLayout.findViewById<TextView>(R.id.detail_txt)
        return StepperItem(viewFlipper, frontTextView, backTextView, descriptionTextView)
    }


    private fun cacheStepperItems(rootLinearLayout: LinearLayout, stepperItem: StepperItem) {
        stepperItemsCache[rootLinearLayout] = stepperItem
    }


    private fun setAllSteppersAttribute(changeSize: Boolean = true) {
        extractStepperItem { stepperItem, index ->
            setAllSteppersAttribute(stepperItem, index, changeSize)
        }
    }

    private fun setAllSteppersAttribute(stepperItem: StepperItem, index: Int, changeSize: Boolean) {
        with(stepperItem) {
            setStepperViewAttributes(index, changeSize, frontTextView, backTextView)
            if (descriptionData.isNotEmpty() && index < descriptionData.size) {
                descriptionTextView.apply {
                    text = descriptionData[index]
                    setTextColor(stepperDescriptionColor)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, stepperDescriptionSize.toFloat())
                }
            }
        }
    }

    private fun setStepperViewAttributes(index: Int, changeSize: Boolean, vararg views: SquareTextView) {
        for (textView in views) {
            setStepperViewAttributes(textView, index, changeSize)
        }
    }


    private fun resetStepperDescriptionText() {
        extractStepperItem { stepperItem, index ->
            val descriptionTextView = stepperItem.descriptionTextView
            descriptionTextView.text = EMPTY_SPACE
            if (descriptionData.isNotEmpty() && index < descriptionData.size) {
                descriptionTextView.apply {
                    text = descriptionData[index]
                    setTextColor(stepperDescriptionColor)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, stepperDescriptionSize.toFloat())
                }

                if (index + 1 == mMaxStepperNumber && !mIsAnimationStarted && mCurrentStepperDescription.currentTextColor != currentStepperDescriptionColor) {
                    resetCurrentStepperDescriptionColor()
                }

            }
        }
    }


    private fun updateStepperDescriptionWithEllipsize(
        ellipse: TextUtils.TruncateAt = TextUtils.TruncateAt.END,
        maxLines: Int = 1
    ) {
        extractStepperItem { stepperItem, index ->
            val descriptionTextView = stepperItem.descriptionTextView
            if (descriptionData.isNotEmpty() && index < descriptionData.size) {
                descriptionTextView.apply {
                    ellipsize = ellipse
                    isSingleLine = maxLines == 1
                    if (maxLines > 1) this.maxLines = maxLines
                }
            }
        }
    }


    private fun resetStepperDescriptionTypeface() {
        extractStepperItem { stepperItem, index ->
            val descriptionTextView = stepperItem.descriptionTextView
            if (descriptionData.isNotEmpty() && index < descriptionData.size) {
                if (stepperDescriptionTypeface != EMPTY_SPACE)
                    descriptionTextView.typeface = getCustomTypeface(stepperDescriptionTypeface)
            }
        }
    }

    private fun resetStepperDescriptionColor() {
        extractStepperItem { stepperItem, index ->
            val descriptionTextView = stepperItem.descriptionTextView
            if (descriptionData.isNotEmpty() && index < descriptionData.size) {
                if (index != mCurrentStepperNumber - 1)
                    descriptionTextView.setTextColor(stepperDescriptionColor)
            }
        }
    }

    private fun resetStepperDescriptionSize() {
        extractStepperItem { stepperItem, index ->
            val descriptionTextView = stepperItem.descriptionTextView
            if (descriptionData.isNotEmpty() && index < descriptionData.size) {
                descriptionTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, stepperDescriptionSize.toFloat())
            }
        }
    }

    private fun resetCurrentStepperDescriptionColor() {
        mCurrentStepperDescription.setTextColor(currentStepperDescriptionColor)
    }


    private fun resetCheckStepperCompleted() {
        extractStepperItem { stepperItem, index ->
            with(stepperItem) {
                checkStepperCompletedAndStepperNumber(frontTextView, index)
                checkStepperCompletedAndStepperNumber(backTextView, index)
            }
        }
    }


    private fun checkStepperCompletedAndStepperNumber(circularTextView: SquareTextView, index: Int) {
        circularTextView.text =
            if (index < mCurrentStepperNumber - 1 && checkStepperCompleted) {
                createCheckedStepper(circularTextView)
            } else {
                if (stepperNumberTypeface != EMPTY_SPACE)
                    circularTextView.typeface = getCustomTypeface(stepperNumberTypeface)
                (index + 1).toString()
            }
    }


    private fun setStepperViewAttributes(circularTextView: SquareTextView, index: Int, changeSize: Boolean) {
        checkStepperCompletedAndStepperNumber(circularTextView, index)
        setStepperColor(circularTextView, index)
        setStepperTextColor(circularTextView, index)
        if (changeSize) {
            setStepperNumberTextSize(circularTextView)
            setStepperSize(circularTextView)
        }
    }


    private fun resetStepperSize() {
        extractStepperItem { stepperItem ->
            resolveStepperSize()
            changeStepperAttribute(stepperItem.frontTextView, stepperItem.backTextView) {
                setStepperNumberTextSize(it)
                setStepperSize(it)
            }

        }
    }

    private fun changeStepperAttribute(vararg views: SquareTextView, changeAction: (SquareTextView) -> Unit) {
        for (textView in views) {
            changeAction(textView)
        }
    }

    private fun setStepperSize(circularTextView: SquareTextView) {
        val layoutParams = circularTextView.layoutParams
        layoutParams.apply {
            width = stepperSize.toInt()
            height = stepperSize.toInt()
        }
        circularTextView.layoutParams = layoutParams
    }

    private fun setStepperTextColor(circularTextView: SquareTextView, index: Int) {
        circularTextView.setTextColor(if (index < mCurrentStepperNumber) stepperNumberForegroundColor else stepperNumberBackgroundColor)
    }

    private fun setStepperNumberTextSize(circularTextView: SquareTextView) {
        circularTextView.textSize = stepperNumberTextSize.pixelToSp()
    }

    private fun resolveStepperSize() {
        resolveStepperSize(stepperSize != 0f, stepperNumberTextSize != 0f)
    }


    private fun resolveStepperSize(isStepperSizeSet: Boolean, isStepperTextSizeSet: Boolean) {
        if (!isStepperSizeSet && !isStepperTextSizeSet) {
            stepperSize = DEFAULT_STEPPER_SIZE.pixelValue()
            stepperNumberTextSize = DEFAULT_TEXT_SIZE.pixelValue(TypedValue.COMPLEX_UNIT_SP)

        } else if (isStepperSizeSet && isStepperTextSizeSet) {
            validateStepperSize()

        } else if (!isStepperSizeSet) {
            stepperSize = stepperNumberTextSize + stepperNumberTextSize / 2

        } else {
            stepperNumberTextSize = stepperSize - (stepperSize * 0.5f)
        }
    }

    private fun validateStepperSize() {
        if (stepperSize <= stepperNumberTextSize) {
            stepperSize = stepperNumberTextSize + stepperNumberTextSize / 2
        }
    }

    private fun createCheckedStepper(circularTextView: SquareTextView): CharSequence? {
        circularTextView.typeface = mCheckFont
        return resources.getText(R.string.check_icon)
    }

    private fun resetStepperColors() {
        extractStepperItem { stepperItem, index ->
            changeStepperAttribute(stepperItem.frontTextView, stepperItem.backTextView) {
                setStepperColor(it, index)
            }
        }
    }

    private fun resetStepperNumberColor() {
        extractStepperItem { stepperItem, index ->
            changeStepperAttribute(stepperItem.frontTextView, stepperItem.backTextView) {
                setStepperTextColor(it, index)
            }
        }
    }

    private fun extractStepperItem(propChangeAction: (StepperItem, Int) -> Unit) {
        stepperItems.withIndex().forEach { indexValue ->
            val stepperItem = stepperItemsCache[indexValue.value]
            stepperItem?.let {
                propChangeAction(it, indexValue.index)
            }
        }
    }

    private fun extractStepperItem(propChangeAction: (StepperItem) -> Unit) {
        stepperItemsCache.forEach { indexValue ->
            val stepperItem: StepperItem? = indexValue.value
            stepperItem?.let {
                propChangeAction(it)
            }
        }
    }


    private fun setStepperColor(circularTextView: SquareTextView, index: Int) {
        val gradientDrawable = circularTextView.background as StateListDrawable
        val drawableContainerState = gradientDrawable.constantState as DrawableContainerState
        val children = drawableContainerState.children
        val selectedItem = children[0] as GradientDrawable?

        resolveSteppersColors(selectedItem, circularTextView, index)
    }

    private fun resolveSteppersColors(selectedItem: GradientDrawable?, circularTextView: SquareTextView, index: Int) {
        selectedItem?.let {
            if ((index < mCurrentStepperNumber - 1) || (index == mCurrentStepperNumber - 1 && circularTextView.id == R.id.viewOneB)) {
                it.setColor(stepperForegroundColor)
            } else {
                it.setColor(stepperBackgroundColor)
            }
        }

    }


    inner class ViewAnimator(private val viewFace: View, private val viewBack: View, private val rootLayout: View) :
        Runnable {
        override fun run() {
            flipView(viewFace, viewBack, rootLayout)
        }

        fun start() {
            postDelayed(this, animStartDelay.toLong())
        }

        fun stop() {
            removeCallbacks(this)
            mAnimator = null
        }
    }


    override fun onViewFlipped() {
        mIsAnimationStarted = false
        resetCurrentStepperDescriptionColor()
    }


    private fun flipView(viewFace: View, viewBack: View, rootLayout: View) {
        val flipAnimation = FlipAnimation3D(viewFace, viewBack)
        if (viewFace.visibility == View.GONE) flipAnimation.reverse()
        flipAnimation.apply {
            viewFlipperListener = this@SoronkoStepper
            startAnim(rootLayout, flipAnimation)
        }
    }


    private fun startCurrentStepperAnimation() {
        mIsAnimationStarted = true
        mAnimator = ViewAnimator(viewOne, viewOneB, viewFlipperOne)
        mAnimator?.start()
    }

    private fun stopCurrentStepperAnimation() {
        mIsAnimationStarted = false
        mAnimator?.stop()
    }

    private fun prepareCurrentStepperViews() {
        val linearLayout = stepperItems[mCurrentStepperNumber - 1]
        val stepperItem = stepperItemsCache[linearLayout]
        stepperItem?.let {
            viewFlipperOne = it.viewFlipper
            viewOne = it.frontTextView
            viewOneB = it.backTextView
            mCurrentStepperDescription = it.descriptionTextView
        }
    }

    private fun clearAllSteppers() {
        removeAllViews()
    }


    private fun <T> stepperProperty(
        default: T, shouldClearAllSteppers: Boolean = false,
        afterChangeAction: (() -> Unit)? = null
    ) = object : ObservableProperty<T>(default) {

        override fun beforeChange(property: KProperty<*>, oldValue: T, newValue: T): Boolean = newValue != oldValue

        override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {

            if (firstInitialized) {
                if (shouldClearAllSteppers) {
                    clearAllSteppers()
                }
                afterChangeAction?.invoke()
            }
        }
    }


    private fun getCustomTypeface(pathToFont: String = FONTAWESOME): Typeface {
        val typeface: Typeface? = Typeface.createFromAsset(context.assets, pathToFont)
        return typeface ?: mDefaultTypefaceBold
    }


    private fun getDefaultTypeface() = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)


    private fun Float.pixelValue(unit: Int = TypedValue.COMPLEX_UNIT_DIP): Float {
        return TypedValue.applyDimension(unit, this, resources.displayMetrics)
    }


    private fun Float.pixelToSp(): Float {
        return this / resources.displayMetrics.scaledDensity
    }

    data class StepperItem(
        var viewFlipper: ViewFlipper, var frontTextView: SquareTextView,
        var backTextView: SquareTextView, var descriptionTextView: TextView
    )


    companion object {

        private const val ROOTS: String = "fonts/"
        private const val FONTAWESOME = "${ROOTS}fontawesome-webfont.ttf"

        private const val DEFAULT_STEPPER_SIZE = 35f
        private const val DEFAULT_TEXT_SIZE = 15f
        private const val EMPTY_SPACE = ""

        private const val DEFAULT_MAX_STEPPER_NUMBER = 5
        private const val DEFAULT_CURRENT_STEPPER_NUMBER = 1

        private const val DEFAULT_ANIM_START_DELAY = 1500

        private const val DEFAULT_DESC_SIZE = 15f

        /** 0 means Steppers creation has not started
         *  Or current stepper number is 1(one)
         */
        private const val DEFAULT_PREVIOUS_STEPPER_NUMBER = 0
    }

    override fun onDetachedFromWindow() {
        stopCurrentStepperAnimation()

        super.onDetachedFromWindow()
    }

}
package com.kofigyan.soronkostepperview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.DrawableContainer.DrawableContainerState
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewFlipper
import com.kofigyan.soronkostepperview.animation.FlipAnimation3D
import com.kofigyan.soronkostepperview.animation.listener.ViewFlipperListener
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty


class SoronkoStepperViewTwo(context: Context, attrs: AttributeSet?, defStyleAttrs: Int) :
    LinearLayout(context, attrs, defStyleAttrs), ViewFlipperListener {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    lateinit var viewFlipperOne: ViewFlipper
    lateinit var viewOne: SquareTextView
    lateinit var viewOneB: SquareTextView
    lateinit var mCurrentStateDescription: TextView


    enum class StepperNumber(val value: Int) {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5)
    }


//    var mBackgroundColor: Int by DelegateInValidateProps(Color.LTGRAY) {
//        resetStepperColors()
//    }

    var mBackgroundColor: Int by stepperProperty(Color.LTGRAY) {
        resetStepperColors()
    }

//    var mForegroundColor: Int by DelegateInValidateProps(Color.rgb(165, 70, 40)) {
//        resetStepperColors()
//    }

    var mForegroundColor: Int by stepperProperty(Color.rgb(165, 70, 40)) {
        resetStepperColors()
    }

//    var mStateNumberBackgroundColor: Int by DelegateInValidateProps(Color.WHITE) {
//        resetStepperNumberColor()
//    }

    var mStateNumberBackgroundColor: Int by stepperProperty(Color.WHITE) {
        resetStepperNumberColor()
    }

//    var mStateNumberForegroundColor: Int by DelegateInValidateProps(Color.WHITE) {
//        resetStepperNumberColor()
//    }

    var mStateNumberForegroundColor: Int by stepperProperty(Color.WHITE) {
        resetStepperNumberColor()
    }

//    var mCurrentStateDescriptionColor: Int by DelegateInValidateProps(Color.rgb(165, 70, 40)) {
//        resetCurrentStateDescriptionColor()
//    }

    var mCurrentStateDescriptionColor: Int by stepperProperty(Color.rgb(165, 70, 40)) {
        resetCurrentStateDescriptionColor()
    }

//    var mStateDescriptionColor: Int by DelegateInValidateProps(Color.LTGRAY) {
//        resetStateDescriptionColor()
//    }

    var mStateDescriptionColor: Int by stepperProperty(Color.LTGRAY) {
        resetStateDescriptionColor()
    }

//    var stepperNumberTextSize: Float by DelegateInValidateProps(0f) {
//        resetStepperSize()
//    }

    var stepperNumberTextSize: Float by stepperProperty(0f) {
        resetStepperSize()
    }

//    var mStateSize: Float by DelegateInValidateProps(0f) {
//        resetStepperSize()
//    }

    var mStateSize: Float by stepperProperty(0f) {
        resetStepperSize()
    }

//    private var mMaxStateNumber by DelegateLayoutProp(DEFAULT_MAX_STATE_NUMBER) {
//        resolveMaxStepperNumberChange()
//    }

    private var mMaxStateNumber by stepperProperty(DEFAULT_MAX_STATE_NUMBER, true) {
        resolveMaxStepperNumberChange()
    }

//    private var mCurrentStateNumber: Int by DelegateLayoutProp(DEFAULT_CURRENT_STATE_NUMBER) {
//        resolveCurrentStepperNumberChange ()
//    }

    private var mCurrentStateNumber: Int by stepperProperty(DEFAULT_CURRENT_STATE_NUMBER, true) {
        resolveCurrentStepperNumberChange()
    }


//    var checkStateCompleted: Boolean by DelegateInValidateProps(false) {
//        resetCheckStateCompleted()
//    }

    var checkStateCompleted: Boolean by stepperProperty(false) {
        resetCheckStateCompleted()
    }

    // var animStartDelay: Int by DelegateInValidateProps(1500)

    var animStartDelay: Int by stepperProperty(DEFAULT_ANIM_START_DELAY)


    private val mCheckFont: Typeface by lazy { getCustomTypeface() }

    private var firstInitialized = false

    // possibly change to stepperProperty but test before doing so
    var descriptionData: Array<String> by DelegateInValidateProps(emptyArray()) {
        resetStateDescriptionText()
    }

    private val stepperItems: MutableList<LinearLayout> = mutableListOf()
    private val stepperItemsCache: MutableMap<LinearLayout, StepperItem> = mutableMapOf()


    init {

        orientation = HORIZONTAL

        attrs?.let {
            val typedArray: TypedArray = context.obtainStyledAttributes(it, R.styleable.SoronkoStepperView)
            initAttrs(typedArray)
            typedArray.recycle()
        }
        firstInitialized = true
    }


    private fun initAttrs(typedArray: TypedArray) {

        mMaxStateNumber = typedArray.getInteger(R.styleable.SoronkoStepperView_ssv_maxStateNumber, mMaxStateNumber)

        mCurrentStateNumber =
            typedArray.getInteger(R.styleable.SoronkoStepperView_ssv_currentStateNumber, mCurrentStateNumber)

        mBackgroundColor =
            typedArray.getColor(R.styleable.SoronkoStepperView_ssv_stateBackgroundColor, mBackgroundColor)
        mForegroundColor =
            typedArray.getColor(R.styleable.SoronkoStepperView_ssv_stateForegroundColor, mForegroundColor)

        checkStateCompleted =
            typedArray.getBoolean(R.styleable.SoronkoStepperView_ssv_checkStateCompleted, checkStateCompleted)

        animStartDelay = typedArray.getInteger(R.styleable.SoronkoStepperView_ssv_animationStartDelay, animStartDelay)

        stepperNumberTextSize =
            typedArray.getDimension(R.styleable.SoronkoStepperView_ssv_stateTextSize, stepperNumberTextSize)

        mStateSize =
            typedArray.getDimension(R.styleable.SoronkoStepperView_ssv_stateSize, mStateSize)


        mStateNumberBackgroundColor =
            typedArray.getColor(
                R.styleable.SoronkoStepperView_ssv_stateNumberBackgroundColor,
                mStateNumberBackgroundColor
            )
        mStateNumberForegroundColor =
            typedArray.getColor(
                R.styleable.SoronkoStepperView_ssv_stateNumberForegroundColor,
                mStateNumberForegroundColor
            )
        mCurrentStateDescriptionColor =
            typedArray.getColor(
                R.styleable.SoronkoStepperView_ssv_currentStateDescriptionColor,
                mCurrentStateDescriptionColor
            )
        mStateDescriptionColor =
            typedArray.getColor(R.styleable.SoronkoStepperView_ssv_stateDescriptionColor, mStateDescriptionColor)


        resolveStateSize()

        validateStateNumber()

        // change name to setupSteppers
        createSteppers()
    }

//    private fun createSteppers() {
//
//        generateSteppers()
//        addSteppersToParentView()
//        cacheStepperItems()
//
//        setAllStatesText()
//        prepareCurrentStateViews()
//        startCurrentStateAnimation()
//    }

    private fun createSteppers() {

        generateSteppers()

        // addSteppersToParentView()
        // cacheStepperItems()
        // setAllStatesText()

        prepareCurrentStateViews()
        startCurrentStateAnimation()
    }

    private fun resolveMaxStepperNumberChange() {
        validateStateNumber() // shd be optional here or possibly remove and add auto resize
        addSteppersToParentView()
    }

    private fun resolveCurrentStepperNumberChange() {
        validateStateNumber() // shd be optional here or possibly remove and add auto resize
        addSteppersToParentView()

        // resolve color and text/check changes here  //text size and state size shd remain same
        setAllStatesText(false)
        prepareCurrentStateViews()
        startCurrentStateAnimation()
    }


    private fun validateStateNumber() {
        if (mCurrentStateNumber > mMaxStateNumber) {
            throw IllegalStateException("Current State $mCurrentStateNumber cannot be greater than Max State Number ${mMaxStateNumber}")
        }
    }

    fun setMaxStateNumber(stepperNumber: StepperNumber) {
        mMaxStateNumber = stepperNumber.value
    }

    fun getMaxStateNumber(): Int {
        return mMaxStateNumber
    }

    fun setCurrentStateNumber(stepperNumber: StepperNumber) {
        mCurrentStateNumber = stepperNumber.value
    }

    fun getCurrentStateNumber(): Int {
        return mCurrentStateNumber
    }


//    private fun generateSteppers() {
//        for (item in 1..DEFAULT_MAX_STATE_NUMBER) {
//            val stepper: LinearLayout =
//                LayoutInflater.from(context).inflate(R.layout.partial_stepper_view_ctv_two, this, false) as LinearLayout
//            //  addView(stepper, index)
//            stepperItems.add(stepper)
//        }
//    }


    private fun generateSteppers() {

        for (index in 0 until DEFAULT_MAX_STATE_NUMBER) {
            val stepperLinearLayout: LinearLayout =
                LayoutInflater.from(context).inflate(R.layout.partial_stepper_view_ctv_two, this, false) as LinearLayout
            addStepperToView(stepperLinearLayout, index)
            stepperItems.add(stepperLinearLayout)
            val stepperItem = createStepperItem(stepperLinearLayout)
            cacheStepperItems(stepperLinearLayout, stepperItem)
            setAllStatesText(stepperItem, index, true)
        }
    }

    private fun addStepperToView(stepper: LinearLayout, index: Int) {
        if (index < mMaxStateNumber)
            addView(stepper, index)
    }


    private fun addSteppersToParentView() {
        for (index in 0 until mMaxStateNumber) {
            addView(stepperItems[index], index)
        }
    }

    private fun cacheStepperItems() {
        stepperItems.forEach { rootLinearLayout ->
            val viewFlipper = rootLinearLayout.findViewById<ViewFlipper>(R.id.viewFlipperOne)
            val frontTextView = rootLinearLayout.findViewById<SquareTextView>(R.id.viewOne)
            val backTextView = rootLinearLayout.findViewById<SquareTextView>(R.id.viewOneB)
            val descriptionTextView = rootLinearLayout.findViewById<TextView>(R.id.detail_txt)
            val stepperItem = StepperItem(viewFlipper, frontTextView, backTextView, descriptionTextView)
            stepperItemsCache.put(rootLinearLayout, stepperItem)
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


//    private fun setAllStatesText(changeSize: Boolean = true) {
//        extractStepperItem { stepperItem, index ->
//            setStepperViewAttributes(stepperItem.frontTextView, index, changeSize)
//            setStepperViewAttributes(stepperItem.backTextView, index, changeSize)
//            if (descriptionData.isNotEmpty() && index < descriptionData.size) {
//                stepperItem.descriptionTextView.text = descriptionData[index]
//                stepperItem.descriptionTextView.setTextColor(mStateDescriptionColor)
//            }
//        }
//    }

    private fun setAllStatesText(changeSize: Boolean = true) {
        extractStepperItem { stepperItem, index ->
            setAllStatesText(stepperItem, index, changeSize)
        }
    }

    private fun setAllStatesText(stepperItem: StepperItem, index: Int, changeSize: Boolean) {
        // setStepperViewAttributes(stepperItem.frontTextView, index, changeSize)
        // setStepperViewAttributes(stepperItem.backTextView, index, changeSize)

        with(stepperItem) {
            setStepperViewAttributes(index, changeSize, frontTextView, backTextView)
            if (descriptionData.isNotEmpty() && index < descriptionData.size) {
                descriptionTextView.apply {
                    text = descriptionData[index]
                    setTextColor(mStateDescriptionColor)
                }
            }
        }
        //  setStepperViewAttributes(index, changeSize, stepperItem.frontTextView, stepperItem.backTextView)

//        if (descriptionData.isNotEmpty() && index < descriptionData.size) {
////            stepperItem.descriptionTextView.text = descriptionData[index]
////            stepperItem.descriptionTextView.setTextColor(mStateDescriptionColor)
//            stepperItem.descriptionTextView.apply {
//                text = descriptionData[index]
//                setTextColor(mStateDescriptionColor)
//            }
//        }
    }

    private fun setStepperViewAttributes(index: Int, changeSize: Boolean, vararg views: SquareTextView) {
        for (textView in views) {
            setStepperViewAttributes(textView, index, changeSize)
        }
    }


    private fun resetStateDescriptionText() {
        extractStepperItem { stepperItem, index ->
            val descriptionTextView = stepperItem.descriptionTextView
            descriptionTextView.text = EMPTY_SPACE_DESCRIPTOR
            if (descriptionData.isNotEmpty() && index < descriptionData.size) {
//                descriptionTextView.text = descriptionData[index]
//                descriptionTextView.setTextColor(mStateDescriptionColor)
                descriptionTextView.apply {
                    text = descriptionData[index]
                    setTextColor(mStateDescriptionColor)
                }

            }
        }
    }


    private fun resetStateDescriptionColor() {
        extractStepperItem { stepperItem, index ->
            val descriptionTextView = stepperItem.descriptionTextView
            if (descriptionData.isNotEmpty() && index < descriptionData.size) {
                if (index != mCurrentStateNumber - 1)
                    descriptionTextView.setTextColor(mStateDescriptionColor)
            }
        }
    }

    private fun resetCurrentStateDescriptionColor() {
        mCurrentStateDescription.setTextColor(mCurrentStateDescriptionColor)
    }


    private fun resetCheckStateCompleted() {
        extractStepperItem { stepperItem, index ->
            checkStateCompletedAndStateNumber(stepperItem.frontTextView, index)
            checkStateCompletedAndStateNumber(stepperItem.backTextView, index)
        }
    }

    private fun checkStateCompletedAndStateNumber(circularTextView: SquareTextView, index: Int) {
        circularTextView.text =
            if (index < mCurrentStateNumber - 1 && checkStateCompleted) createCheckedState(circularTextView) else (index + 1).toString()
    }

//    private fun setStepperViewAttributes(circularTextView: SquareTextView, index: Int, changeSize: Boolean = true) {
//        checkStateCompletedAndStateNumber(circularTextView, index)
//        setStepperColor(circularTextView, index)
//        setStepperTextColor(circularTextView, index)
//        if (changeSize) {
//            setStepperNumberTextSize(circularTextView)
//            setStepperSize(circularTextView)
//        }
//    }

    private fun setStepperViewAttributes(circularTextView: SquareTextView, index: Int, changeSize: Boolean) {
        checkStateCompletedAndStateNumber(circularTextView, index)
        setStepperColor(circularTextView, index)
        setStepperTextColor(circularTextView, index)
        if (changeSize) {
            setStepperNumberTextSize(circularTextView)
            setStepperSize(circularTextView)
        }
    }


    private fun resetStepperSize() {
        extractStepperItem { stepperItem, index ->
            resolveStateSize()

            // setStepperNumberTextSize(stepperItem.frontTextView)
            // setStepperNumberTextSize(stepperItem.backTextView)
//            setStepperSize(stepperItem.frontTextView)
//            setStepperSize(stepperItem.backTextView)

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
            width = mStateSize.toInt()
            height = mStateSize.toInt()
        }
        circularTextView.layoutParams = layoutParams
    }

    private fun setStepperTextColor(circularTextView: SquareTextView, index: Int) {
        circularTextView.setTextColor(if (index < mCurrentStateNumber) mStateNumberForegroundColor else mStateNumberBackgroundColor)
    }

    private fun setStepperNumberTextSize(circularTextView: SquareTextView) {
        //  circularTextView.textSize = convertPixelToSp(stepperNumberTextSize)
        // just commented
        circularTextView.textSize = convertPixelToSp(stepperNumberTextSize)
        //   circularTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX , stepperNumberTextSizeInt.toFloat())
    }

    private fun resolveStateSize() {
        resolveStateSize(mStateSize != 0f, stepperNumberTextSize != 0f)
    }


    private fun resolveStateSize(isStateSizeSet: Boolean, isStateTextSizeSet: Boolean) {
        if (!isStateSizeSet && !isStateTextSizeSet) {
            mStateSize = convertDpToPixel(DEFAULT_STATE_SIZE)
            stepperNumberTextSize = convertSpToPixel(DEFAULT_TEXT_SIZE)

        } else if (isStateSizeSet && isStateTextSizeSet) {
            validateStateSize()

        } else if (!isStateSizeSet) {
            mStateSize = stepperNumberTextSize + stepperNumberTextSize / 2

        } else {
            // stepperNumberTextSize = mStateSize - mStateSize * 0.375f
            stepperNumberTextSize = mStateSize - (mStateSize * 0.5f)
        }
    }

    private fun validateStateSize() {
        if (mStateSize <= stepperNumberTextSize) {
            mStateSize = stepperNumberTextSize + stepperNumberTextSize / 2
        }
    }

    private fun createCheckedState(circularTextView: SquareTextView): CharSequence? {
        circularTextView.typeface = mCheckFont
        return resources.getText(R.string.check_icon)
    }

    private fun resetStepperColors() {
        extractStepperItem { stepperItem, index ->
            //  setStepperColor(stepperItem.frontTextView, index)
            //  setStepperColor(stepperItem.backTextView, index)
            changeStepperAttribute(stepperItem.frontTextView, stepperItem.backTextView) {
                setStepperColor(it, index)
            }
        }
    }

    private fun resetStepperNumberColor() {
        extractStepperItem { stepperItem, index ->
            //  setStepperTextColor(stepperItem.frontTextView, index)
            //  setStepperTextColor(stepperItem.backTextView, index)
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


    private fun setStepperColor(circularTextView: SquareTextView, index: Int) {
        val gradientDrawable = circularTextView.background as StateListDrawable
        val drawableContainerState = gradientDrawable.constantState as DrawableContainerState
        val children = drawableContainerState.children
        val selectedItem = children[0] as GradientDrawable?
        val unselectedItem = children[1] as GradientDrawable?
        //   val selectedDrawable = selectedItem.getDrawable(0) as GradientDrawable
        //   val unselectedDrawable = unselectedItem.getDrawable(0) as GradientDrawable

        // selectedItem?.setColor(Color.parseColor("#FD00DF"))

        resolveSteppersColors(selectedItem, circularTextView, index)

        //selectedItem?.setColor(if (circularTextView.id == R.id.viewOneB) mForegroundColor else mBackgroundColor)

        //selectedDrawable.setStroke(STORKE_SIZE, NOTIFICATION_COLOR)
        // unselectedDrawable.setStroke(STORKE_SIZE, NOTIFICATION_COLOR)
    }

    private fun resolveSteppersColors(selectedItem: GradientDrawable?, circularTextView: SquareTextView, index: Int) {
//        if (index < mCurrentStateNumber - 1) {
//            selectedItem?.setColor(mForegroundColor)
//        } else if (index == mCurrentStateNumber - 1 && circularTextView.id == R.id.viewOneB) {
//            selectedItem?.setColor(mForegroundColor)
//        } else {
//            selectedItem?.setColor(mBackgroundColor)
//        }

        selectedItem?.let {
            if ((index < mCurrentStateNumber - 1) || (index == mCurrentStateNumber - 1 && circularTextView.id == R.id.viewOneB)) {
                it.setColor(mForegroundColor)
            } else {
                it.setColor(mBackgroundColor)
            }
        }

    }


    inner class ViewAnimator(private val viewFace: View, private val viewBack: View, private val rootLayout: View) :
        Runnable {
        override fun run() {
            flipView(viewFace, viewBack, rootLayout)
        }
    }

    override fun onViewFlipped() {
        mCurrentStateDescription.setTextColor(mCurrentStateDescriptionColor)
    }


    private fun flipView(viewFace: View, viewBack: View, rootLayout: View) {
        val flipAnimation = FlipAnimation3D(viewFace, viewBack)
        if (viewFace.visibility == View.GONE) flipAnimation.reverse()
        flipAnimation.apply {
            viewFlipperListener = this@SoronkoStepperViewTwo
            startAnim(rootLayout, flipAnimation)
        }
    }

    private fun startCurrentStateAnimation() {
        postDelayed(ViewAnimator(viewOne, viewOneB, viewFlipperOne), animStartDelay.toLong())
    }

    private fun prepareCurrentStateViews() {
        val linearLayout = stepperItems[mCurrentStateNumber - 1]
        val stepperItem = stepperItemsCache[linearLayout]
        stepperItem?.let {
            viewFlipperOne = it.viewFlipper
            viewOne = it.frontTextView
            viewOneB = it.backTextView
            mCurrentStateDescription = it.descriptionTextView
        }
    }

    private fun clearAllSteppers() {
        removeAllViews()
    }


    inner class DelegateLayoutProp<T>(private var field: T, private inline var func: () -> Unit = {}) {

        operator fun setValue(thisRef: Any?, p: KProperty<*>, v: T) {
            field = v

            if (firstInitialized) {
                // stepperItems.clear()
                Toast.makeText(context, "layout reached", Toast.LENGTH_LONG).show()
                clearAllSteppers()
                func()
                //  requestLayout()
            }
        }

        operator fun getValue(thisRef: Any?, p: KProperty<*>): T {
            return field
        }
    }

    inner class DelegateInValidateProps<T>(private var field: T, private inline var func: () -> Unit = {}) {

        operator fun setValue(thisRef: Any?, p: KProperty<*>, v: T) {
            field = v

            if (firstInitialized) {
                func()
                Toast.makeText(context, "invalidate reached", Toast.LENGTH_LONG).show()
                //   invalidate()
            }
        }

        operator fun getValue(thisRef: Any?, p: KProperty<*>): T {
            return field
        }

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
                    Toast.makeText(context, "all cleared reached", Toast.LENGTH_LONG).show()
                }
                afterChangeAction?.invoke()
                Toast.makeText(context, "observable prop reached", Toast.LENGTH_LONG).show()
            }
        }

    }


    private fun getCustomTypeface() = Typeface.createFromAsset(context.assets, FONTAWESOME)

    private fun Float.pixelValue(unit: Int = TypedValue.COMPLEX_UNIT_DIP): Float {
        return TypedValue.applyDimension(unit, this, resources.displayMetrics)
    }

    private fun convertDpToPixel(dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale
    }

    private fun convertSpToPixel(sp: Float): Float {
        val scale = resources.displayMetrics.scaledDensity
        return sp * scale
    }

    private fun convertPixelToSp(px: Float): Float {
        return px / resources.displayMetrics.scaledDensity
    }

    data class StepperItem(
        var viewFlipper: ViewFlipper, var frontTextView: SquareTextView,
        var backTextView: SquareTextView, var descriptionTextView: TextView
    )


    companion object {
        private const val ROOTS: String = "fonts/"
        private const val FONTAWESOME = "${ROOTS}fontawesome-webfont.ttf"

        private const val DEFAULT_STATE_SIZE = 35f
        private const val DEFAULT_TEXT_SIZE = 15f
        private const val EMPTY_SPACE_DESCRIPTOR = ""

        private const val DEFAULT_MAX_STATE_NUMBER = 5
        private const val DEFAULT_CURRENT_STATE_NUMBER = 1

        private const val DEFAULT_ANIM_START_DELAY = 1500
    }

}
package com.kofigyan.soronkostepperview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import com.kofigyan.soronkostepperview.animation.FlipAnimation3D
import com.kofigyan.soronkostepperview.animation.listener.ViewFlipperListener
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableContainer.DrawableContainerState
import android.graphics.drawable.StateListDrawable
import android.util.TypedValue
import android.widget.Toast
import java.util.ArrayList
import kotlin.reflect.KProperty


class SoronkoStepperViewTwoBackUp(context: Context, attrs: AttributeSet?, defStyleAttrs: Int) :
    LinearLayout(context, attrs, defStyleAttrs), ViewFlipperListener {


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    lateinit var viewFlipperOne: ViewFlipper
    lateinit var viewOne: SquareTextView
    lateinit var viewOneB: SquareTextView
    lateinit var mCurrentStateDescription: TextView

    // private val mHandler = Handler()

    var mBackgroundColor: Int by DelegateInValidateProps(Color.GRAY) {
        resetStepperColors()
    }

    var mForegroundColor: Int by DelegateInValidateProps(Color.BLUE) {
        resetStepperColors()
    }

    var mStateNumberBackgroundColor: Int by DelegateInValidateProps(Color.WHITE) {
        resetStepperNumberColor()
    }

    var mStateNumberForegroundColor: Int by DelegateInValidateProps(Color.WHITE) {
        resetStepperNumberColor()
    }


    var mCurrentStateDescriptionColor: Int by DelegateInValidateProps(Color.BLUE) {
        resetCurrentStateDescriptionColor()
    }


    var mStateDescriptionColor: Int by DelegateInValidateProps(Color.GRAY) {
        resetStateDescriptionColor()
    }


    var stepperNumberTextSize: Float = 0.toFloat()

    var stepperNumberTextSizeInt: Int = 0

    var mStateSize: Float = 0.toFloat()

    var mMaxStateNumber by DelegateLayoutProp(5) {
        // createSteppers()
        resolveMaximumStateNumberChanges()
    }


    var mCurrentStateNumber: Int by DelegateLayoutProp(1) {
        //  createSteppers()
        resolveCurrentStateNumberChanges()
    }

    var checkStateCompleted: Boolean by DelegateInValidateProps(false) {
        // createSteppers()
        resetCheckStateCompleted()
    }

    private val mCheckFont: Typeface by lazy { getCustomTypeface() }

    var animStartDelay: Int by DelegateInValidateProps(1500)

    private var firstInitialized = false

    // private val descriptionData = arrayOf("Details", "Status", "Photo", "Confirm", "Sent")
    var descriptionData: Array<String?> by DelegateInValidateProps(emptyArray()) {
        // createSteppers()
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

//        stepperNumberTextSizeInt =
//            typedArray.getDimensionPixelSize(R.styleable.SoronkoStepperView_ssv_stateTextSize, stepperNumberTextSizeInt)


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

        createSteppers()
    }

    private fun createSteppers() {

        generateSteppers()
        addSteppersToParentView()
        cacheStepperItems()

        setAllStatesText()
        prepareCurrentStateViews()
        startCurrentStateAnimation()
    }

    private fun resolveMaximumStateNumberChanges() {
        validateStateNumber() // shd be optional here or possibly remove and add auto resize
        addSteppersToParentView()
    }

    private fun resolveCurrentStateNumberChanges() {
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

//    private fun generateSteppers() {
//        for (index in 0 until mMaxStateNumber) {
//            val stepper: LinearLayout =
//                LayoutInflater.from(context).inflate(R.layout.partial_stepper_view_ctv_two, this, false) as LinearLayout
//            addView(stepper, index)
//            stepperItems.add(stepper)
//        }
//    }

    private fun generateSteppers() {
        for (item in 1..MAX_STATE_NUMBER) {
            val stepper: LinearLayout =
                LayoutInflater.from(context).inflate(R.layout.partial_stepper_view_ctv_two, this, false) as LinearLayout
            //  addView(stepper, index)
            stepperItems.add(stepper)
        }
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

//    private fun setAllStatesText(changeSize: Boolean = true) {
////        stepperItems.withIndex().map {
////            traverseViewGroupAndSetText(it.value, it.index)
////        }
//
//        stepperItems.withIndex().forEach { indexedValue ->
//            //  traverseViewGroupAndSetText(it.value, it.index)
//            val stepperItem = stepperItemsCache[indexedValue.value]
//            stepperItem?.let {
//                setStepperViewAttributes(it.frontTextView, indexedValue.index, changeSize)
//                setStepperViewAttributes(it.backTextView, indexedValue.index, changeSize)
//                if (descriptionData.isNotEmpty() && indexedValue.index < descriptionData.size) {
//                    it.descriptionTextView.text = descriptionData[indexedValue.index]
//                    it.descriptionTextView.setTextColor(mStateDescriptionColor)
//                }
//            }
//        }
//
//
////        for (index in stepperItems.indices) {
////            val rootLayout = stepperItems[index]
////            traverseViewGroupAndSetText(rootLayout, index)
////        }
//    }


    private fun setAllStatesText(changeSize: Boolean = true) {

//        stepperItems.withIndex().forEach { indexedValue ->
//            //  traverseViewGroupAndSetText(it.value, it.index)
//            val stepperItem = stepperItemsCache[indexedValue.value]
//            stepperItem?.let {
//                setStepperViewAttributes(it.frontTextView, indexedValue.index, changeSize)
//                setStepperViewAttributes(it.backTextView, indexedValue.index, changeSize)
//                if (descriptionData.isNotEmpty() && indexedValue.index < descriptionData.size) {
//                    it.descriptionTextView.text = descriptionData[indexedValue.index]
//                    it.descriptionTextView.setTextColor(mStateDescriptionColor)
//                }
//            }
//        }

        extractStepperItem { stepperItem, index ->
            //  Toast.makeText(context, "description reached", Toast.LENGTH_LONG).show()
            setStepperViewAttributes(stepperItem.frontTextView, index, changeSize)
            setStepperViewAttributes(stepperItem.backTextView, index, changeSize)
            if (descriptionData.isNotEmpty() && index < descriptionData.size) {
                stepperItem.descriptionTextView.text = descriptionData[index]
                stepperItem.descriptionTextView.setTextColor(mStateDescriptionColor)
                //    Toast.makeText(context, "description reached", Toast.LENGTH_LONG).show()
            }
        }

    }

//    private fun resetStateDescriptionText() {
//        stepperItems.withIndex().map { indexValue ->
//            // val descriptionTextView = it.value.findViewById<TextView>(R.id.detail_txt)
//            val stepperItem = stepperItemsCache[indexValue.value]
//            stepperItem?.let {
//                val descriptionTextView = stepperItem.descriptionTextView
//                descriptionTextView.text = EMPTY_SPACE_DESCRIPTOR
//                if (descriptionData.isNotEmpty() && indexValue.index < descriptionData.size) {
//                    descriptionTextView.text = descriptionData[indexValue.index]
//                    descriptionTextView.setTextColor(mStateDescriptionColor)
//                }
//            }
//        }
//
////        for (index in stepperItems.indices){
////            val view = stepperItems[index]
////            val descriptionTextView = view.findViewById<TextView>(R.id.detail_txt)
////            descriptionTextView.text = EMPTY_SPACE_DESCRIPTOR
////            if (descriptionData.isNotEmpty() && index < descriptionData.size) descriptionTextView.text = descriptionData[index]
////
////        }
//
//
//    }


    private fun resetStateDescriptionText() {
        extractStepperItem { stepperItem, index ->
            val descriptionTextView = stepperItem.descriptionTextView
            descriptionTextView.text = EMPTY_SPACE_DESCRIPTOR
            if (descriptionData.isNotEmpty() && index < descriptionData.size) {
                descriptionTextView.text = descriptionData[index]
                descriptionTextView.setTextColor(mStateDescriptionColor)
                //  Toast.makeText(context, "new description reached", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun resetStateDescriptionColor() {
        extractStepperItem { stepperItem, index ->
            val descriptionTextView = stepperItem.descriptionTextView
            // descriptionTextView.text = EMPTY_SPACE_DESCRIPTOR
            if (descriptionData.isNotEmpty() && index < descriptionData.size) {
                //  descriptionTextView.text = descriptionData[index]
                if (index != mCurrentStateNumber - 1 )
                descriptionTextView.setTextColor(mStateDescriptionColor)
                //  Toast.makeText(context, "new description reached", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun resetCurrentStateDescriptionColor() {
        mCurrentStateDescription.setTextColor(mCurrentStateDescriptionColor)
    }


    private fun traverseViewGroupAndSetText(v: View, index: Int) {
        if (v is ViewGroup) {
            for (i in 0 until v.childCount) {
                val child = v.getChildAt(i)
                traverseViewGroupAndSetText(child, index)
            }
        } else if (v is TextView) {
            if (v.getParent() is ViewFlipper) {
                setStepperViewAttributes(v as SquareTextView, index)
                // updateStepperCurrentColor(v.parent as ViewFlipper , index)
            } else {
                if (descriptionData.isNotEmpty() && index < descriptionData.size) v.text = descriptionData[index]
            }
        }
    }

    private fun resetCheckStateCompleted() {

//        stepperItems.withIndex().forEach { indexValue ->
//            val stepperItem = stepperItemsCache[indexValue.value]
//            stepperItem?.let {
//                checkStateCompletedAndStateNumber(it.frontTextView, indexValue.index)
//                checkStateCompletedAndStateNumber(it.backTextView, indexValue.index)
//            }
//        }

        extractStepperItem { stepperItem, index ->
            checkStateCompletedAndStateNumber(stepperItem.frontTextView, index)
            checkStateCompletedAndStateNumber(stepperItem.backTextView, index)
        }

    }

    private fun checkStateCompletedAndStateNumber(circularTextView: SquareTextView, index: Int) {
        circularTextView.text =
            if (index < mCurrentStateNumber - 1 && checkStateCompleted) createCheckedState(circularTextView) else (index + 1).toString()
    }

    private fun setStepperViewAttributes(circularTextView: SquareTextView, index: Int, changeSize: Boolean = true) {

//        circularTextView.text =
//            if (index < mCurrentStateNumber - 1 && checkStateCompleted) createCheckedState(circularTextView) else (index + 1).toString()

        checkStateCompletedAndStateNumber(circularTextView, index)

        setStepperBackgroundColor(circularTextView, index)
        setStepperTextColor(circularTextView, index)
        if (changeSize) {
            setStepperNumberTextSize(circularTextView)
            setStepperSize(circularTextView)
        }
    }

    private fun setStepperSize(circularTextView: SquareTextView) {
        val layoutParams = circularTextView.layoutParams
        layoutParams.apply {
            width = mStateSize.toInt()
            height = mStateSize.toInt()
        }
        //  layoutParams.width = mStateSize.toInt()
        //  layoutParams.height = mStateSize.toInt()
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

//        stepperItems.withIndex().forEach { indexValue ->
//            val stepperItem = stepperItemsCache[indexValue.value]
//            stepperItem?.let {
//                setStepperBackgroundColor(it.frontTextView, indexValue.index)
//                setStepperBackgroundColor(it.backTextView, indexValue.index)
//            }
//        }

        extractStepperItem { stepperItem, index ->
            setStepperBackgroundColor(stepperItem.frontTextView, index)
            setStepperBackgroundColor(stepperItem.backTextView, index)
        }
    }

    private fun resetStepperNumberColor() {
        extractStepperItem { stepperItem, index ->
            setStepperTextColor(stepperItem.frontTextView, index)
            setStepperTextColor(stepperItem.backTextView, index)
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


    private fun setStepperBackgroundColor(circularTextView: SquareTextView, index: Int) {
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
        if (index < mCurrentStateNumber - 1) {
            selectedItem?.setColor(mForegroundColor)
        } else if (index == mCurrentStateNumber - 1 && circularTextView.id == R.id.viewOneB) {
            selectedItem?.setColor(mForegroundColor)
        } else {
            selectedItem?.setColor(mBackgroundColor)
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
            viewFlipperListener = this@SoronkoStepperViewTwoBackUp
            startAnim(rootLayout, flipAnimation)
        }

//        if (viewFace.visibility == View.GONE) flipAnimation.reverse()
//        flipAnimation.viewFlipperListener = this
//        flipAnimation.startAnim(rootLayout, flipAnimation)
    }

    private fun startCurrentStateAnimation() {
        //  mHandler.postDelayed(ViewAnimator(viewOne, viewOneB, viewFlipperOne), animStartDelay.toLong())
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

//        viewFlipperOne = linearLayout.getChildAt(0) as ViewFlipper
//        viewFlipperOne.apply {
//            viewOne = getChildAt(0) as SquareTextView
//            viewOneB = getChildAt(1) as SquareTextView
//        }
//        val view = linearLayout.getChildAt(1)
//        if (view is TextView) mCurrentStateDescription = view

    }

    private fun clearAllSteppers() {
        removeAllViews()
    }


    inner class DelegateLayoutProp<T>(private var field: T, private inline var func: () -> Unit = {}) {

        operator fun setValue(thisRef: Any?, p: KProperty<*>, v: T) {
            field = v

            if (firstInitialized) {
                // stepperItems.clear()
                Toast.makeText(context, "reached", Toast.LENGTH_LONG).show()
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

        private const val DEFAULT_STATE_SIZE = 25f
        private const val DEFAULT_TEXT_SIZE = 15f
        private const val EMPTY_SPACE_DESCRIPTOR = ""

        private const val MAX_STATE_NUMBER = 5

    }

}
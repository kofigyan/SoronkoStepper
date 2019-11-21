package com.kofigyan.soronkostepperview

import android.content.Context
import android.content.res.TypedArray
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

class SoronkoStepperView(context: Context, attrs: AttributeSet?, defStyleAttrs: Int) :
    LinearLayout(context, attrs, defStyleAttrs), ViewFlipperListener {


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    lateinit var viewFlipperOne: ViewFlipper
    lateinit var viewOne: CircularTextView
    lateinit var viewOneB: CircularTextView
    lateinit var mCurrentStateDescription: TextView

    val mHandler = Handler()

    var mMaxStateNumber: Int = 5
    var mCurrentStateNumber: Int = 1

    private val descriptionData = arrayOf("Details", "Status", "Photo", "Confirm", "Sent")

    private val stepperItems: MutableList<LinearLayout> = mutableListOf()


    init {

        orientation = HORIZONTAL

        attrs?.let {

            val typedArray: TypedArray = context.obtainStyledAttributes(it, R.styleable.SoronkoStepperView)
            initAttrs(typedArray)
            typedArray.recycle()
        }
    }


    private fun initAttrs(typedArray: TypedArray) {

        mMaxStateNumber = typedArray.getInteger(R.styleable.SoronkoStepperView_ssv_maxStateNumber, mMaxStateNumber)
        mCurrentStateNumber =
            typedArray.getInteger(R.styleable.SoronkoStepperView_ssv_currentStateNumber, mCurrentStateNumber)

        generateSteppers()

        setAllStatesText()

        prepareCurrentStateViews(mCurrentStateNumber)

        startCurrentStateAnimation()
    }


    private fun generateSteppers() {
        for (index in 0 until mMaxStateNumber) {
            val stepper: LinearLayout =
                LayoutInflater.from(context).inflate(R.layout.partial_stepper_view_ctv, this, false) as LinearLayout
            addView(stepper, index)
            stepperItems.add(stepper)
        }
    }

    private fun setAllStatesText() {

        for (index in stepperItems.indices) {
            val rootLayout = stepperItems[index]
            traverseViewGroupAndSetText(rootLayout, index)
        }

    }


    private fun traverseViewGroupAndSetText(v: View, index: Int) {
        if (v is ViewGroup) {
            for (i in 0 until v.childCount) {
                val child = v.getChildAt(i)
                traverseViewGroupAndSetText(child, index)
            }
        } else if (v is TextView) {
            if (v.getParent() is ViewFlipper) {
                setStepperViewAttributes(v as CircularTextView, index)
            } else {
                v.text = descriptionData[index]
            }
        }
    }

    private fun setStepperViewAttributes(circularTextView: CircularTextView, index: Int) {
        circularTextView.text = (index + 1).toString()
    }


    inner class ViewAnimator(private val viewFace: View,private val viewBack: View,private val rootLayout: View) : Runnable {
        override fun run() {
            flipView(viewFace, viewBack, rootLayout)
        }
    }

    override fun onViewFlipped() {

    }


    private fun flipView(viewFace: View, viewBack: View, rootLayout: View) {

        val flipAnimation: FlipAnimation3D = FlipAnimation3D(viewFace, viewBack)

        if (viewFace.visibility == View.GONE) flipAnimation.reverse()

        flipAnimation.viewFlipperListener = this
        flipAnimation.startAnim(rootLayout, flipAnimation)
    }

    private fun startCurrentStateAnimation() {
        mHandler.postDelayed(ViewAnimator(viewOne, viewOneB, viewFlipperOne), 1500)
    }

    private fun prepareCurrentStateViews(index: Int) {

        val linearLayout = stepperItems.get(index - 1)

        viewFlipperOne = linearLayout.getChildAt(0) as ViewFlipper

        viewOne = viewFlipperOne.getChildAt(0) as CircularTextView

        viewOneB = viewFlipperOne.getChildAt(1) as CircularTextView

        val view = linearLayout.getChildAt(1)

        if (view is TextView)
            mCurrentStateDescription = view

    }


}
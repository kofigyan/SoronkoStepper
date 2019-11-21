package com.kofigyan.soronkostepperview.activity

import android.os.Bundle
import com.kofigyan.soronkostepperview.R
import kotlinx.android.synthetic.main.activity_change_state_size.*

class ChangeStepperSizeActivity : BaseDescriptionActivity() {

    override val layout: Int
        get() = R.layout.activity_change_state_size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_coloring_steppers)

        //  tv_size_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.ssv_state_text_size))
        tv_size_text.textSize = resources.getDimension(R.dimen.ssv_state_text_size)
        mStepper.stepperDescriptionSize = resources.getDimension(R.dimen.ssv_state_text_size).toInt()
    }

}
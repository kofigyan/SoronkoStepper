package com.kofigyan.soronkostepperview.activity

import android.os.Bundle
import com.kofigyan.soronkostepperview.R
import com.kofigyan.soronkostepperview.util.ROBOTO_LIGHT_TYPEFACE

class StepperFourCheckActivity : BaseDescriptionActivity() {

    override val layout: Int
        get() = R.layout.activity_check_four_stepper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mStepper.stepperNumberTypeface = ROBOTO_LIGHT_TYPEFACE
        mStepper.stepperDescriptionTypeface = ROBOTO_LIGHT_TYPEFACE
    }

}
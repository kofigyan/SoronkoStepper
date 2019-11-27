package com.kofigyan.soronkostepperview.activity

import android.os.Bundle
import com.kofigyan.soronkostepperview.R

class StepperFourCheckActivity : BaseDescriptionActivity() {

    override val layout: Int
        get() = R.layout.activity_check_four_stepper

    private val descriptionData4 = arrayOf("Details\nPlace", "Status\nPrice", "Photo\nShoot", "Confirm\nResponse", "Buy\nDone")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mStepper.descriptionData = descriptionData4
       // mStepper.descriptionMultilineTruncateEnd = 2
    }

}
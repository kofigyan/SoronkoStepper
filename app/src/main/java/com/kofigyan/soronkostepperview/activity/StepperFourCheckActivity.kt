package com.kofigyan.soronkostepperview.activity

import android.os.Bundle
import com.kofigyan.soronkostepperview.R
import kotlinx.android.synthetic.main.activity_check_four_stepper.*

class StepperFourCheckActivity : BaseDescriptionActivity() {

    override val layout: Int
        get() = R.layout.activity_check_four_stepper

   // private val descriptionData4 = arrayOf("encyclopedia", "dictionaries", "bibliography", "librarian" ,"hostorian")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        mStepper.descriptionData = descriptionData4
//        mStepper.descriptionTruncateEnd = true

        soronko_stepper_two.descriptionData = descriptionData
        soronko_stepper_three.descriptionData = descriptionData
        soronko_stepper_four.descriptionData = descriptionData
      //  soronko_stepper_two.descriptionData = descriptionData


    }

}
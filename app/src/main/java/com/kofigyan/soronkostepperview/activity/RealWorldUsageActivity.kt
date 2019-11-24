package com.kofigyan.soronkostepperview.activity

import android.os.Bundle
import com.kofigyan.soronkostepper.SoronkoStepper
import com.kofigyan.soronkostepperview.R
import kotlinx.android.synthetic.main.activity_real_world.*

class RealWorldUsageActivity : BaseActivity() {

    override val layout: Int
        get() = R.layout.activity_real_world

    private val descriptionData = arrayOf("Details", "Status", "Photo", "Confirm", "Done")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        soronko_stepper.descriptionData = descriptionData
        //  soronko_stepper.descriptionTruncateEnd = true
        soronko_stepper.descriptionMultilineTruncateEnd = 2

        btn_next_page.setOnClickListener {
            moveToPage(soronko_stepper.getCurrentStepperNumber())
        }

        btn_prev_page.setOnClickListener {
            moveToPage(soronko_stepper.getCurrentStepperNumber(), false)
        }
    }


    private fun moveToPage(currentPage: Int, isNextButtonClick: Boolean = true) {

        when (currentPage) {

            1 -> {
                soronko_stepper.setCurrentStepperNumber(SoronkoStepper.StepperNumber.TWO)
            }

            2 -> {
                soronko_stepper.setCurrentStepperNumber(
                    if (isNextButtonClick) SoronkoStepper.StepperNumber.THREE else SoronkoStepper.StepperNumber.ONE
                )
            }

            3 -> {
                soronko_stepper.setCurrentStepperNumber(if (isNextButtonClick) SoronkoStepper.StepperNumber.FOUR else SoronkoStepper.StepperNumber.TWO)
            }

            4 -> {
                soronko_stepper.setCurrentStepperNumber(if (isNextButtonClick) SoronkoStepper.StepperNumber.FIVE else SoronkoStepper.StepperNumber.THREE)
            }

            5 -> {
                if (!isNextButtonClick)
                    soronko_stepper.setCurrentStepperNumber(SoronkoStepper.StepperNumber.FOUR)
            }

        }

    }

}
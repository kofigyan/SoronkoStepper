package com.kofigyan.soronkostepperview.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.kofigyan.soronkostepper.SoronkoStepper
import com.kofigyan.soronkostepper.SoronkoStepper.StepperNumber.ONE

import com.kofigyan.soronkostepperview.R
import kotlinx.android.synthetic.main.activity_real_world.*

class RealWorldUsageActivity : BaseActivity() {

    override val layout: Int
        get() = R.layout.activity_real_world

    private val descriptionData = arrayOf("Details", "Status", "Photo", "Confirm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        soronko_stepper.descriptionData = descriptionData
        //  soronko_stepper.descriptionTruncateEnd = true
        soronko_stepper.descriptionMultilineTruncateEnd = 2

        btn_next_page.setOnClickListener {
            moveToPage(soronko_stepper.getCurrentStepperNumber() + 1)
        }

        btn_prev_page.setOnClickListener {
            moveToPage(soronko_stepper.getCurrentStepperNumber() - 1)
        }

        resolveButtonsVisibility(1)

    }


    private fun moveToPage(pageNumber: Int) {
        soronko_stepper.setCurrentStepperNumber(intToStepperNumber(pageNumber) ?: ONE)
        resolveButtonsVisibility(pageNumber)
    }

    private fun resolveButtonsVisibility(pageNumber: Int) {
        when (pageNumber) {

            1 -> btn_prev_page.visibility = View.GONE

            2, 3 -> makeViewVisible(btn_next_page, btn_prev_page)

            4 -> btn_next_page.visibility = View.GONE

        }
    }

    private fun makeViewVisible(vararg views: Button) {
        for (textView in views) {
            if (textView.visibility == View.GONE) textView.visibility = View.VISIBLE
        }
    }


    companion object {
        // reverse lookup of enum StepperNumber
        private val map = SoronkoStepper.StepperNumber.values().associateBy(SoronkoStepper.StepperNumber::value)

        fun intToStepperNumber(type: Int) = map[type]
    }

}
package com.kofigyan.soronkostepperview.activity

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.kofigyan.soronkostepper.SoronkoStepper
import com.kofigyan.soronkostepperview.R
import kotlinx.android.synthetic.main.activity_real_world.*

class RealWorldUsageActivity : BaseActivity() {

    override val layout: Int
        get() = R.layout.activity_real_world

    private val descriptionData = arrayOf("DetailsDetailsDetailsDetailsDetails", "Status", "Photo", "Confirm", "Done")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        soronko_stepper.descriptionData = descriptionData
      //  soronko_stepper.descriptionTruncateEnd = true
        soronko_stepper.descriptionMultilineTruncateEnd  = 2

        btn_next_page.setOnClickListener {
            moveToNextPage(soronko_stepper.getCurrentStepperNumber())
        }
    }


    private fun moveToNextPage(currentPage: Int) {

        when (currentPage) {

            1 -> {
                soronko_stepper.setCurrentStepperNumber(SoronkoStepper.StepperNumber.TWO)
            }

            2 -> {
                soronko_stepper.setCurrentStepperNumber(SoronkoStepper.StepperNumber.THREE)
            }

            3 -> {

                soronko_stepper.setCurrentStepperNumber(SoronkoStepper.StepperNumber.FOUR)
            }

            4 -> {

                soronko_stepper.setCurrentStepperNumber(SoronkoStepper.StepperNumber.FIVE)
            }

        }

    }


}
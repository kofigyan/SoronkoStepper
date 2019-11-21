package com.kofigyan.soronkostepperview.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kofigyan.soronkostepperview.R
import com.kofigyan.soronkostepperview.SoronkoStepperViewTwo
import kotlinx.android.synthetic.main.activity_main.*
import com.kofigyan.soronkostepperview.SoronkoStepperViewTwo.StepperNumber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //  ssv_view.mBackgroundColor = Color.BLUE

        ssv_view.mCurrentStateDescriptionColor = Color.YELLOW

        ssv_view.descriptionData = arrayOf("Details", "Status", "Photo", "Confirm", "Sent")
        ssv_view_three.descriptionData = arrayOf("Details", "Status", "Photo", "Confirm", "Sent")
        btn_click.setOnClickListener {
            // ssv_view.mCurrentStateNumber = ssv_view.mCurrentStateNumber + 1
            //  ssv_view.mMaxStateNumber = 3

            //   ssv_view.mForegroundColor = Color.MAGENTA
            // ssv_view.mBackgroundColor = Color.MAGENTA
            //  ssv_view.checkStateCompleted = true

        //    ssv_view.stepperNumberTextSize = resources.getDimension(R.dimen.ssv_size)
            // ssv_view.mStateSize = resources.getDimension(R.dimen.ssv_size)

            //  ssv_view.mStateNumberForegroundColor = Color.BLUE

          //  ssv_view.mStateDescriptionColor = Color.BLACK


           // ssv_view.setMaxStateNumber(StepperNumber.FIVE)

         ssv_view.setCurrentStateNumber(nextState(ssv_view.getCurrentStateNumber()))

//            Handler().postDelayed(Runnable {
//
//                ssv_view.mStateSize = resources.getDimension(R.dimen.ssv_state_size)
//                ssv_view.setCurrentStateNumber(nextState(ssv_view.getCurrentStateNumber()))
//
//            }, 2000)
        }
    }

  private  fun nextState(current: Int): SoronkoStepperViewTwo.StepperNumber {
        return when (current) {
            StepperNumber.FIVE.value -> SoronkoStepperViewTwo.StepperNumber.FIVE
            StepperNumber.FOUR.value -> SoronkoStepperViewTwo.StepperNumber.FIVE
            StepperNumber.THREE.value -> SoronkoStepperViewTwo.StepperNumber.FOUR
            StepperNumber.TWO.value -> SoronkoStepperViewTwo.StepperNumber.THREE
            StepperNumber.ONE.value -> SoronkoStepperViewTwo.StepperNumber.TWO
            else -> SoronkoStepperViewTwo.StepperNumber.FIVE
        }
    }
}

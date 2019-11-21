package com.kofigyan.soronkostepperview.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.kofigyan.soronkostepperview.R

abstract class BaseDescriptionActivity : BaseActivity() {

    private val descriptionData = arrayOf("Details", "Status", "Photo", "Confirm", "Done")
    private val descriptionData3 = arrayOf("Details", "Status", "Photo", "Confirm", "Done")
    private val descriptionData2 = arrayOf("Mango", "Apple", "Photo", "Confirm", "Done")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mStepper.descriptionData = descriptionData

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_description, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.decriptionColor -> {
                with(mStepper) {
                    currentStepperDescriptionColor = ContextCompat.getColor(
                        this@BaseDescriptionActivity,
                        R.color.description_foreground_color
                    )

                    stepperDescriptionColor = ContextCompat.getColor(
                        this@BaseDescriptionActivity,
                        R.color.description_background_color
                    )
                }
            }

            R.id.decriptionSize -> {
                with(mStepper) {
                    stepperDescriptionSize = 40
                    //  stepperNumberTextSize = 20f
                }
            }

            R.id.decriptionDataChange -> {
                with(mStepper) {
                    this.descriptionData = this@BaseDescriptionActivity.descriptionData3
                }
            }


            R.id.decriptionTypeface -> {
                with(mStepper) {
                    stepperDescriptionTypeface = "fonts/Roboto-Light.ttf"
                }
            }
//
//
//            R.id.max_state -> {
//                with(mStepper) {
//                    if (getCurrentStateNumber() <= SoronkoStepper.StepperNumber.FOUR.value)
//                        setMaxStateNumber(SoronkoStepper.StepperNumber.FOUR)
//                }
//            }
//
//            R.id.check_state_completed -> {
//                mStepper.checkStateCompleted = true
//            }

        }

        return true
    }


}
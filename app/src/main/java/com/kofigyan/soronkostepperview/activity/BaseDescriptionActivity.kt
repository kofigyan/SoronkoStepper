package com.kofigyan.soronkostepperview.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.kofigyan.soronkostepperview.R
import com.kofigyan.soronkostepperview.util.ROBOTO_LIGHT_TYPEFACE


abstract class BaseDescriptionActivity : BaseActivity() {

    private val descriptionData = arrayOf("Details", "Status", "Photo", "Confirm", "Done")

    private val descriptionDataChange = arrayOf("Apple", "Mango", "Orange", "Tomato", "Done")

    private val descriptionDataEllipsized = arrayOf("DetailsDetailsDetailsDetailsDetails", "Status", "Photo", "Confirm", "Done")


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
                }
            }

            R.id.decriptionDataChange -> {
                with(mStepper) {
                    this.descriptionData = this@BaseDescriptionActivity.descriptionDataChange
                }
            }


            R.id.decriptionTypeface -> {
                with(mStepper) {
                    stepperDescriptionTypeface = ROBOTO_LIGHT_TYPEFACE
                }
            }

            R.id.decriptionEllipsized  -> {
                with(mStepper) {
                    descriptionData = this@BaseDescriptionActivity.descriptionDataEllipsized
                    descriptionTruncateEnd = true
                }
            }

            R.id.decriptionMultiline  -> {
                with(mStepper) {
                    descriptionData = this@BaseDescriptionActivity.descriptionDataEllipsized
                    descriptionMultilineTruncateEnd = 2
                }
            }

        }

        return true
    }


}
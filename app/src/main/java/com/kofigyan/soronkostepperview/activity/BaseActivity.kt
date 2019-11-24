package com.kofigyan.soronkostepperview.activity

import android.graphics.Typeface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kofigyan.soronkostepper.SoronkoStepper
import com.kofigyan.soronkostepperview.R
import com.kofigyan.soronkostepperview.util.ROBOTO_LIGHT_TYPEFACE


abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var mStepper: SoronkoStepper

    protected abstract val layout: Int

    private val robotoLightTypeface: Typeface by lazy { getTypeface() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        mStepper = findViewById(R.id.soronko_stepper)

    }

    private fun getTypeface(): Typeface {
        return Typeface.createFromAsset(getAssets(), ROBOTO_LIGHT_TYPEFACE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_base, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.color -> {
                with(mStepper) {
                    stepperForegroundColor =
                        ContextCompat.getColor(this@BaseActivity, R.color.demo_state_foreground_color)
                    stepperBackgroundColor = ContextCompat.getColor(this@BaseActivity, android.R.color.darker_gray)
                    stepperNumberForegroundColor = ContextCompat.getColor(this@BaseActivity, android.R.color.white)
                    stepperNumberBackgroundColor =
                        ContextCompat.getColor(
                            this@BaseActivity,
                            android.R.color.background_dark
                        )
                }
            }

            R.id.size -> {
                with(mStepper) {
                    stepperSize = 40f
                    stepperNumberTextSize = 20f
                }
            }


            R.id.current_state -> {
                with(mStepper) {
                    if (getMaxStepperNumber() >= SoronkoStepper.StepperNumber.TWO.value)
                        setCurrentStepperNumber(SoronkoStepper.StepperNumber.TWO)
                }
            }

            R.id.custom_typeface -> {
                with(mStepper) {
                    stepperNumberTypeface = ROBOTO_LIGHT_TYPEFACE
                }
            }


            R.id.max_state -> {
                with(mStepper) {
                    if (getCurrentStepperNumber() <= SoronkoStepper.StepperNumber.FOUR.value)
                        setMaxStepperNumber(SoronkoStepper.StepperNumber.FOUR)
                }
            }

            R.id.check_state_completed -> {
                mStepper.checkStepperCompleted = true
            }

        }

        return true
    }


}
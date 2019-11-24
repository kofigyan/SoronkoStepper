package com.kofigyan.soronkostepperview.activity

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kofigyan.soronkostepper.SoronkoStepper
import com.kofigyan.soronkostepper.SoronkoStepper.StepperNumber.ONE
import com.kofigyan.soronkostepper.SoronkoStepper.StepperNumber.TWO
import com.kofigyan.soronkostepper.SoronkoStepper.StepperNumber.THREE
import com.kofigyan.soronkostepper.SoronkoStepper.StepperNumber.FOUR

import com.kofigyan.soronkostepperview.R
import com.kofigyan.soronkostepperview.util.FONTAWESOME_TYPEFACE
import kotlinx.android.synthetic.main.general_account_content.*
import kotlinx.android.synthetic.main.general_footer_account_content.*
import kotlinx.android.synthetic.main.general_stepper_progress_bar.*

class GeneralAccountActivity : AppCompatActivity() {

    private val fontTypeface: Typeface by lazy { getFontAwesomeTypeface() }

    private val stepperDescriptionData =
        arrayOf("Account", "Amount", "Deposit", "Confirm")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_account_completed)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = resources.getString(R.string.stepper_soronko_demo)
        }


        soronko_stepper.descriptionData = stepperDescriptionData

        tv_next_btn.applyTypefaceToView()
        tv_back_btn.applyTypefaceToView()
        tv_complete_correct_sign.applyTypefaceToView()

        tv_next_btn.setOnClickListener {
            moveToPage(soronko_stepper.getCurrentStepperNumber())
        }

        tv_back_btn.setOnClickListener {
            moveToPage(soronko_stepper.getCurrentStepperNumber(), false)
        }

    }

    private fun TextView.applyTypefaceToView() {
        this.typeface = fontTypeface
    }

    private fun getFontAwesomeTypeface(): Typeface {
        return Typeface.createFromAsset(assets, FONTAWESOME_TYPEFACE)
    }


    private fun moveToPage(currentPage: Int, fromNextButton: Boolean = true) {

        when (currentPage) {

            1 -> {
                if (fromNextButton)
                    soronko_stepper.setCurrentStepperNumber(SoronkoStepper.StepperNumber.TWO)
            }

            2 -> {
                soronko_stepper.setCurrentStepperNumber(
                    if (fromNextButton) SoronkoStepper.StepperNumber.THREE else SoronkoStepper.StepperNumber.ONE
                )
            }

            3 -> {
                soronko_stepper.setCurrentStepperNumber(if (fromNextButton) SoronkoStepper.StepperNumber.FOUR else SoronkoStepper.StepperNumber.TWO)
            }

            4 -> {
                if (!fromNextButton)
                    soronko_stepper.setCurrentStepperNumber(SoronkoStepper.StepperNumber.THREE)
            }

        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.viewGithub -> {
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://github.com/kofigyan/SoronkoStepper")
                }.also(::startActivity)
            }

            R.id.feedback -> {

                Intent(
                    Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "kofigyan2011@gmail.com", null
                    )
                ).apply {
                    putExtra(Intent.EXTRA_SUBJECT, "SoronkoStepper Feedback")
                    putExtra(Intent.EXTRA_TEXT, "Your feedback here...")
                }.also {
                    startActivity(Intent.createChooser(it, "Feedback"))
                }

            }

            R.id.stepper_features -> {
                Intent(this, MainActivity::class.java).also(::startActivity)
            }

            android.R.id.home -> finish()

        }

        return true
    }


}
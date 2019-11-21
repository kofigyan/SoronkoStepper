package com.kofigyan.soronkostepperview.activity

import android.os.Bundle
import android.widget.Toast
import com.kofigyan.soronkostepperview.R
import com.kofigyan.soronkostepperview.adapter.StepperApiFeatureAdapter
import com.kofigyan.soronkostepperview.model.StepperApiFeature
import com.kofigyan.soronkostepperview.util.ActivitySelector
import com.kofigyan.soronkostepperview.util.startAppActivity

class MainActivityTwo : ListBaseActivity() {

    private val apiFeatureList by lazy {
        getApiFeatures()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main_two)

        val adapter = StepperApiFeatureAdapter()
        rv.adapter = adapter

        adapter.setupApiFeatures(apiFeatureList)

        adapter.onItemClickListener = { position ->
            startAppActivity(ActivitySelector.allActivities[position])
        }

    }


    private fun getApiFeatures(): List<StepperApiFeature> {

        val apiFeatureList = mutableListOf<StepperApiFeature>()

        val titles: Array<String> = resources.getStringArray(R.array.features_titles)
        val descriptions: Array<String> = resources.getStringArray(R.array.features_descriptions)

        titles.forEachIndexed { index, title ->
            val description = descriptions[index]
            apiFeatureList.add(StepperApiFeature(title, description))
        }

        return apiFeatureList
    }

}
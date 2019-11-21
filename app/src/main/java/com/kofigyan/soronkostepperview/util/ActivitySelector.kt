package com.kofigyan.soronkostepperview.util

import com.kofigyan.soronkostepperview.activity.*

object ActivitySelector {

    val allActivities: List<Class<out BaseActivity>> =
        listOf(StepperTwoBasicActivity::class.java, ColoringStepperActivity::class.java, StepperFourCheckActivity::class.java
        , DescriptionFourStepperActivity::class.java,ChangeStepperSizeActivity::class.java ,RealWorldUsageActivity::class.java)

}
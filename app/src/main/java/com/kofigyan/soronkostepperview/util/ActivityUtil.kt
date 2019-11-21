package com.kofigyan.soronkostepperview.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.kofigyan.soronkostepperview.activity.BaseActivity

fun AppCompatActivity.startAppActivity(clazz: Class<out BaseActivity>) {
    startActivity(Intent(this, clazz))
}
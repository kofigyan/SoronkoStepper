package com.kofigyan.soronkostepperview.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kofigyan.soronkostepperview.R


abstract class ListBaseActivity : AppCompatActivity() {

    protected lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectCommonViews()
    }


    private fun injectCommonViews() {
        recyclerview = findViewById(R.id.rv)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.setHasFixedSize(true)
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        when (item.itemId) {
//
//            R.id.viewGithub -> {
//                Intent(Intent.ACTION_VIEW).apply {
//                    data = Uri.parse("https://github.com/kofigyan/StateProgressBar")
//                }.also(::startActivity)
//            }
//
//            R.id.feedback -> {
//
//                Intent(
//                    Intent.ACTION_SENDTO, Uri.fromParts(
//                        "mailto", "kofigyan2011@gmail.com", null
//                    )
//                ).apply {
//                    putExtra(Intent.EXTRA_SUBJECT, "StateProgressBar Feedback")
//                    putExtra(Intent.EXTRA_TEXT, "Your feedback here...")
//                }.also {
//                    startActivity(Intent.createChooser(it, "Feedback"))
//                }
//
//            }
//
//        }
//
//        return true
//    }

}
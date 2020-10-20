package com.example.taskaddressmodule.utils

import android.app.Activity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast


fun Activity.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, msg, duration).show()

fun Activity.showProgressBar(progressBar: ProgressBar, show: Boolean = true) {
    if (show) {
        progressBar.visibility = View.VISIBLE
    } else {
        progressBar.visibility = View.GONE
    }
}
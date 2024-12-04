package com.muhia.damaris.interview.weatherapp.utils

import android.app.Activity
import android.app.AlertDialog
import com.muhia.damaris.interview.weatherapp.R
import java.text.SimpleDateFormat
import java.util.Locale

object Factory {

    fun String.formatDate(from: String, to: String): String {
        val inputFormatter = SimpleDateFormat(from, Locale.getDefault())
        val date = inputFormatter.parse(this)
        return date?.let {
            val outputFormatter = SimpleDateFormat(to, Locale.getDefault())
            outputFormatter.format(date)
        } ?: ""
    }

    fun Activity.showErrorDialog(msg:String) {
        AlertDialog.Builder(this)
            .setMessage(msg)
            .setPositiveButton(R.string.ok) { _, _ ->
                this.finish()
            }
            .setCancelable(false) // Prevent dismissal by tapping outside
            .show()
    }



}
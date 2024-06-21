package com.capbatu.workify

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    fun EditText.getInputValue() = text.toString().trim()

    fun showToast(
        context: Context,
        message: String,
    ) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun String.toDate() = SimpleDateFormat("dd-MM-yyyy,HH:mm", Locale.ROOT).parse(this)

    fun getDate(): String = DateFormat.getDateInstance(DateFormat.FULL).format(Date())

    fun getGreeting(context: Context): String {
        val currentDateTime = Date().time
        val currentHour = currentDateTime.toInt() % 24

        val greeting =
            when (currentHour) {
                in 0..11 -> context.getString(R.string.greetings_good_morning)
                in 12..17 -> context.getString(R.string.greetings_good_afternoon)
                in 18..20 -> context.getString(R.string.greetings_good_evening)
                else -> context.getString(R.string.greetings_good_night)
            }
        return greeting
    }

    fun String.isValidUrl(): Boolean {
        return Patterns.WEB_URL.matcher(this).matches()
    }
}

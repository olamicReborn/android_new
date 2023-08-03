package com.maggnet.utils

import android.content.Context
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class DateUpdates : DateInterface {
    override fun is24HourFormat(context: Context): Boolean {
        return DateFormat.is24HourFormat(context)
    }

    override fun format(date: Date,context: Context): String {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy")
        return dateFormat.format(date)
    }
}
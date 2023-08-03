package com.maggnet.utils

import android.content.Context
import java.util.*

interface DateInterface {
    fun is24HourFormat(context: Context):Boolean
    fun format(date: Date,context: Context):String
}
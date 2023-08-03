package com.maggnet.utils

import com.maggnet.BuildConfig
import timber.log.Timber


object AppLogger {
    init {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun d(tag: String, message: String, vararg params: Any) {
        try {
            Timber.tag(tag).d(message, params)
        } catch (e: Exception) {
            Timber.e("I can not log this debug message")
        }
    }

    fun i(tag: String, message: String, vararg params: Any) {
        try {
            Timber.tag(tag).i(message, params)
        } catch (e: Exception) {
            Timber.e("I can not log this info message")
        }
    }

    fun v(tag: String, message: String, vararg params: Any) {
        try {
            Timber.tag(tag).v(message, params)
        } catch (e: Exception) {
            Timber.e("I can not log verbose message")
        }
    }

    fun e(tag: String, message: String, vararg params: Any) {
        try {
            Timber.tag(tag).e(message, params)
        } catch (e: Exception) {
            Timber.e("I can not log this error message")
        }
    }

    fun w(tag: String, message: String, vararg params: Any) {
        try {
            Timber.tag(tag).w(message, params)
        } catch (e: Exception) {
            Timber.e("I can not log this WTF (What a terrible failure) message")
        }
    }

}
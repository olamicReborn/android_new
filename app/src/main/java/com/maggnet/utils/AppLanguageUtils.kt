package com.maggnet.utils

import android.content.Context
import com.maggnet.MaggnetApplication
import com.yariksoffice.lingver.Lingver

object AppLanguageUtils {

    enum class Language(val code: String) {
        ENGLISH("en"),
        ARABIC("ar")
    }

    fun initAppLocale(appContext: MaggnetApplication, language: Language) {
        Lingver.init(appContext, language.code)
    }

    fun setLanguageTo(appContext: Context, langCode: String) {
        Lingver.Companion.getInstance().setLocale(appContext, langCode)
    }

}
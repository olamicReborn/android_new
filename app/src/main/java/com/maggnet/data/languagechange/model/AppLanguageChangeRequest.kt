package com.maggnet.data.languagechange.model

import com.google.gson.annotations.SerializedName

data class AppLanguageChangeRequest(
    @SerializedName("app_language") val appLanguage: Int,
)
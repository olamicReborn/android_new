package com.maggnet.utils

import android.content.Context

class FileBackend {
    private val FILE_PROVIDER = ".files"

    fun getAuthority(context: Context): String? {
        return context.packageName + FILE_PROVIDER
    }

}
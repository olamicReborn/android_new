package com.maggnet.utils

import android.content.Context
import android.net.Uri

open class FileUtils {

    fun getPath(context: Context, uri: Uri?): String? {
        if (uri == null) {
            return null
        }
        if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

}
package com.maggnet.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.maggnet.R
import com.maggnet.ui.extensions.getUrlFromString
import com.maggnet.utils.Utils.isValidUrl

fun viewURL(context: Context, url: String) {

    var urlToLoad = url
    try {

        if (isValidUrl(url).not()) {
            return
        }

        if (url.startsWith("http://").not() || url.startsWith("https://").not()) {
            urlToLoad = "https://$urlToLoad"
        }

        val viewIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(urlToLoad.getUrlFromString())
        )

        context.startActivity(
            viewIntent
        )

    } catch (activityNotFound: ActivityNotFoundException) {
        showFailureDialog(context)
    } catch (security: SecurityException) {
        showFailureDialog(context)
    }
}

fun showFailureDialog(context: Context) {
    val dialogManager = DialogManager()
    dialogManager.singleButtonDialog(
        title = context.getString(R.string.failure),
        message = context.getString(R.string.activity_not_found_exception),
        cancelable = true,
        context = context
    )
}


fun inviteFriends(context: Context, inviteCode : String) {

    try {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, context.getString(R.string.maggnet_app_invite) + "  "+ inviteCode + "\n"+ context.getString(R.string.maggnet_app_link))
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    } catch (activityNotFound: ActivityNotFoundException) {
        val dialogManager = DialogManager()
        dialogManager.singleButtonDialog(
            title = context.getString(R.string.failure),
            message = context.getString(R.string.activity_not_found_exception),
            cancelable = true,
            context = context
        )
    }
}
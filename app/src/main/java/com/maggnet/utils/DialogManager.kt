package com.maggnet.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.text.Html
import android.view.*
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maggnet.MaggnetApplication
import com.maggnet.R
import com.makeramen.roundedimageview.RoundedImageView
import java.io.File
import javax.inject.Inject


class DialogManager @Inject constructor() {

    /**
     * Create and displays alert dialog with single button
     * * @param context - Calling view/activity context
     * @param title - Dialog title
     * @param message - Dialog message
     * @param positiveButtonText - text for the dialog button
     * @param alertDialogListener - listener  , if callback requested
     *
     * */
    fun singleButtonDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonText: String = context.getString(R.string.ok),
        alertDialogListener: AlertDialogListener? = null,
        cancelable: Boolean,
    ) {
        try {
            val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)
            builder.setTitle(title)
                .setMessage(message)
                .setCancelable(cancelable)
                .setPositiveButton(positiveButtonText) { dialogInterface, _ ->
                    alertDialogListener?.onPositiveButtonClicked()
                    dialogInterface?.dismiss()
                }

            val alertDialog = builder.create()
            alertDialog.show()
        } catch (e: IllegalStateException) {
            AppLogger.e(TAG, "singleButtonDialog " + e.localizedMessage)
        }
    }

    /**
     * Create and displays alert dialog with two button (Action and cancel )
     * @param context - Calling view/activity context
     * @param title - Dialog title
     * @param message - Dialog message
     * @param positiveButtonText - text for the positive dialog button
     * @param negativeButtonText - text for the negative dialog button
     * @param alertDialogListener - listener  , if callback requested
     * @param cancelable - boolean  , dialog cancellable or not
     *
     * */
    fun twoButtonDialog(
        context: Context, title: String, message: String,
        positiveButtonText: String = context.getString(R.string.ok),
        negativeButtonText: String = context.getString(R.string.cancel),
        alertDialogListener: AlertDialogListener? = null,
        cancelable: Boolean = true,
    ): AlertDialog {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        builder.setCancelable(cancelable)
        builder.setIcon(R.drawable.call_fab_icon)
        builder.setPositiveButton(positiveButtonText) { dialogInterface, _ ->
            alertDialogListener?.onPositiveButtonClicked()
            dialogInterface?.dismiss()
        }.setNegativeButton(negativeButtonText) { dialogInterface, _ ->
            alertDialogListener?.onNegativeButtonClicked()
            dialogInterface.dismiss()
        }
        builder.setTitle(title)
        builder.setMessage(message)
        val alertDialog = builder.create()
        alertDialog.setOnDismissListener { alertDialogListener?.onDialogDismissed() }
        alertDialog.show()
        return alertDialog
    }

    /**
     * Create and displays alert dialog with two button (Action and cancel )
     * @param context - Calling view/activity context
     * @param title - Dialog title
     * @param message - Dialog message
     * @param positiveButtonText - text for the positive dialog button
     * @param negativeButtonText - text for the negative dialog button
     * @param alertDialogListener - listener  , if callback requested
     *
     * */

    fun twoButtonDialog(
        context: Context,
        title: String,
        message: String,
        spannedMessage: Boolean = false,
        spannedTitle: Boolean = false,
        positiveButtonText: String = context.getString(R.string.ok),
        negativeButtonText: String = context.getString(R.string.cancel),
        alertDialogListener: AlertDialogListener? = null,
    ) {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        builder
            .setPositiveButton(positiveButtonText) { dialogInterface, _ ->
                alertDialogListener?.onPositiveButtonClicked()
                dialogInterface?.dismiss()
            }
            .setNegativeButton(negativeButtonText) { dialogInterface, _ ->
                alertDialogListener?.onNegativeButtonClicked()
                dialogInterface.dismiss()
            }

        if (spannedTitle) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setTitle(Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY))
            } else {
                @Suppress("DEPRECATION")
                builder.setTitle(Html.fromHtml(title))
            }
        } else {
            builder.setTitle(title)
        }

        if (spannedMessage) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setMessage(Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY))
            } else {
                @Suppress("DEPRECATION")
                builder.setMessage(Html.fromHtml(message))
            }
        } else {
            builder.setMessage(message)
        }

        val alertDialog = builder.create()
        alertDialog.setOnDismissListener { alertDialogListener?.onDialogDismissed() }
        alertDialog.show()
    }

    /**
     * Custom alert dialog interface to process/handle click
     * **/
    interface AlertDialogListener {

        fun onPositiveButtonClicked() {
            // Implementation not required
        }

        fun onNegativeButtonClicked() {
            // Implementation not required
        }

        fun onNeutralButtonClicked() {
            // Implementation not required
        }

        fun onDialogDismissed() {
            // Implementation not required
        }
    }




    private fun languageEnglishSelection(
        checkBoxArabic: CheckBox,
        checkBoxEnglish: CheckBox,
        englishLayout: ConstraintLayout,
        arabicLayout: ConstraintLayout,
        txtEnglish: TextView,
        txtArabic: TextView
    ) {
        checkBoxArabic.isChecked = false
        checkBoxEnglish.isChecked = true
        arabicLayout.backgroundTintList =
            ContextCompat.getColorStateList(MaggnetApplication.instance, R.color.bg_light_grey)
        englishLayout.backgroundTintList =
            ContextCompat.getColorStateList(MaggnetApplication.instance, R.color.blue)
        txtArabic.setTextColor(
            ContextCompat.getColor(
                MaggnetApplication.instance,
                R.color.dark_grey
            )
        )
        txtEnglish.setTextColor(
            ContextCompat.getColor(
                MaggnetApplication.instance,
                R.color.white
            )
        )
    }

    private fun languageArabicSelection(
        checkBoxArabic: CheckBox,
        checkBoxEnglish: CheckBox,
        englishLayout: ConstraintLayout,
        arabicLayout: ConstraintLayout,
        txtEnglish: TextView,
        txtArabic: TextView
    ) {
        checkBoxArabic.isChecked = true
        checkBoxEnglish.isChecked = false
        arabicLayout.backgroundTintList =
            ContextCompat.getColorStateList(MaggnetApplication.instance, R.color.blue)
        englishLayout.backgroundTintList =
            ContextCompat.getColorStateList(MaggnetApplication.instance, R.color.bg_light_grey)
        txtArabic.setTextColor(
            ContextCompat.getColor(
                MaggnetApplication.instance,
                R.color.white
            )
        )
        txtEnglish.setTextColor(
            ContextCompat.getColor(
                MaggnetApplication.instance,
                R.color.dark_grey
            )
        )
    }


    fun twoItemDialog(
        context: Context?,
        firstItem: String,
        secondItem: String,
        alertDialogListener: AlertDialogItemClickListener? = null,
    ) {
        val alertDialogBuilder: AlertDialog.Builder =
            AlertDialog.Builder(context, R.style.AlertDialogTheme)
        val items = arrayOf(firstItem, secondItem)
        alertDialogBuilder.setItems(items) { dialogInterface, i ->
            alertDialogListener?.onItemClicked(i)
            dialogInterface?.dismiss()
        }
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    interface AlertDialogCameraListener {

        fun onCameraClick() {
            // Implementation not required
        }

        fun onGalleryClick() {
            // Implementation not required
        }

        fun onShownImageClick(uri: Uri) {
            // Implementation not required
        }

    }

    interface AlertDialogItemClickListener {
        fun onItemClicked(which: Int) {
            // Implementation not required
        }

        fun onItemClicked(which: String) {
            // Implementation not required
        }

        fun onDialogDismissed() {}
    }


}
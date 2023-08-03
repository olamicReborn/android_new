package com.maggnet.utils

import android.content.Context
import android.graphics.*
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.text.TextUtils
import android.webkit.MimeTypeMap
import com.maggnet.R
import com.maggnet.ui.extensions.safeGet
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    var dateInterface: DateInterface = DateUpdates()

    fun isConnectingToInternet(context: Context): Boolean {
        val connectivity = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null)
                for (i in info)
                    if (i.state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
        }
        return false
    }


    fun isAndroidRAndAbove(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
    }


    val androidQAndAbove: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q


    @JvmStatic
    fun isValidUrl(url: String?): Boolean {
        if (url == null) return false
        val p = android.util.Patterns.WEB_URL
        val m = p.matcher(url)
        return if (m.matches()) true else {
            try {
                while (m.find()) {
                    val furl = m.group()
                    return true
                }
            } catch (ignored: Exception) {
            }
            false
        }
    }

    fun compressImage(filePath: String?): File? {
        try {
            var scaledBitmap: Bitmap? = null
            val options =
                BitmapFactory.Options()
            options.inJustDecodeBounds = true
            var bmp =
                BitmapFactory.decodeFile(filePath, options)
            var actualHeight = options.outHeight
            var actualWidth = options.outWidth
            val maxHeight = 816.0f
            val maxWidth = 612.0f
            var imgRatio = actualWidth / actualHeight.toFloat()
            val maxRatio = maxWidth / maxHeight
            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight
                    actualWidth = (imgRatio * actualWidth).toInt()
                    actualHeight = maxHeight.toInt()
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth
                    actualHeight = (imgRatio * actualHeight).toInt()
                    actualWidth = maxWidth.toInt()
                } else {
                    actualHeight = maxHeight.toInt()
                    actualWidth = maxWidth.toInt()
                }
            }
            options.inSampleSize =
                calculateInSampleSize(options, actualWidth, actualHeight)
            options.inJustDecodeBounds = false
            options.inDither = false
            options.inPurgeable = true
            options.inInputShareable = true
            options.inTempStorage = ByteArray(2 * 1048576)
            try {
                bmp = BitmapFactory.decodeFile(filePath, options)
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()
            }
            try {
                scaledBitmap = Bitmap.createBitmap(
                    actualWidth,
                    actualHeight,
                    Bitmap.Config.ARGB_8888
                )
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()
            }
            val ratioX = actualWidth / options.outWidth.toFloat()
            val ratioY = actualHeight / options.outHeight.toFloat()
            val middleX = actualWidth / 2.0f
            val middleY = actualHeight / 2.0f
            val scaleMatrix = Matrix()
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)
            val canvas: Canvas
            if (scaledBitmap != null) {
                canvas = Canvas(scaledBitmap)
                canvas.setMatrix(scaleMatrix)
                canvas.drawBitmap(
                    bmp,
                    middleX - bmp.width / 2,
                    middleY - bmp.height / 2,
                    Paint(Paint.FILTER_BITMAP_FLAG)
                )
            }
            val exif: ExifInterface
            try {
                exif = ExifInterface(filePath.safeGet())
                val orientation =
                    exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
                val matrix = Matrix()
                when (orientation) {
                    6 -> {
                        matrix.postRotate(90f)
                    }
                    3 -> {
                        matrix.postRotate(180f)
                    }
                    8 -> {
                        matrix.postRotate(270f)
                    }
                }
                if (scaledBitmap != null) {
                    scaledBitmap = Bitmap.createBitmap(
                        scaledBitmap,
                        0,
                        0,
                        scaledBitmap.width,
                        scaledBitmap.height,
                        matrix,
                        true
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val out: FileOutputStream
            try {
                out = FileOutputStream(filePath)
                scaledBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, out)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return File(filePath)
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val heightRatio =
                Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio =
                Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        val totalPixels = width * height.toFloat()
        val totalReqPixelsCap = reqWidth * reqHeight * 2.toFloat()
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++
        }
        return inSampleSize
    }

    /**
     * @return The MIME type for the given file.
     */
    fun getMimeType(file: File): String? {
        val extension = getExtension(file.name)
        if (!TextUtils.isEmpty(extension)) {
            val mimeTypeFromExtension = MimeTypeMap.getSingleton()
                .getMimeTypeFromExtension(extension.substring(1).toLowerCase())
            return if (TextUtils.isEmpty(mimeTypeFromExtension))
                MimeTypeUtil.getType(
                file.name
            ) else mimeTypeFromExtension
        }
        return "application/octet-stream"
    }

    /**
     * Gets the extension of a file name, like ".png" or ".jpg".
     *
     * @param uri
     * @return Extension including the dot("."); "" if there is no extension;
     * null if uri was null.
     */
    fun getExtension(uri: String?): String {
        if (uri == null) {
            return ""
        }
        val dot = uri.lastIndexOf(".")
        return if (dot >= 0) {
            var substring = uri.substring(dot)
            if (substring.contains("?")) {
                substring = substring.substring(0, substring.indexOf("?"))
            }
            substring
        } else {
            // No extension.
            ""
        }
    }

    fun getMilliFromDate(dateToFormat: String?): Long {
        return if (dateToFormat?.isEmpty() == true) {
            0
        } else {
            var date = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ROOT)
            formatter.timeZone = TimeZone.getTimeZone("GMT");
            try {
                date = formatter.parse(dateToFormat)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            date.time
        }
    }


    /**
     * To make custom time or date for notification
     *
     * @param context
     * @param time
     * @return formatted date
     */
    fun customTimeForNotification(context: Context, time: Long): String? {
        if (time == 0L) {
            return ""
        }
        val date = Date(time)
        return if (today(date)) {
            returnIs24HourFormat(context, date)
        } else if (yesterday(date)) {
            context.getString(R.string.yesterday)
        } else {
            dateInterface.format(date, context)
        }
    }

    private fun today(date: Date): Boolean {
        return sameDay(date, Date(System.currentTimeMillis()))
    }

    private fun sameDay(a: Date, b: Date): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = a
        cal2.time = b
        return (cal1[Calendar.YEAR] == cal2[Calendar.YEAR]
                && cal1[Calendar.DAY_OF_YEAR] == cal2[Calendar.DAY_OF_YEAR])
    }

    private fun returnIs24HourFormat(context: Context, date: Date?): String? {
        return if (Utils.dateInterface.is24HourFormat(context)) SimpleDateFormat(
            "HH:mm",
            Locale.getDefault()
        ).format(date) else SimpleDateFormat("hh:mm a", Locale.getDefault()).format(date).uppercase(
            Locale.getDefault()
        )
    }

    private fun yesterday(date: Date): Boolean {
        return yesterday(date.time)
    }

    fun yesterday(date: Long): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.add(Calendar.DAY_OF_YEAR, -1)
        cal2.time = Date(date)
        return (cal1[Calendar.YEAR] == cal2[Calendar.YEAR]
                && cal1[Calendar.DAY_OF_YEAR] == cal2[Calendar.DAY_OF_YEAR])
    }


}
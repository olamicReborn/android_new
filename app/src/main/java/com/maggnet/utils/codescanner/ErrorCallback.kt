
package com.maggnet.utils.codescanner

import androidx.annotation.WorkerThread

/**
 * Code scanner error callback
 */
interface ErrorCallback {
    /**
     * Called when error has occurred
     * <br></br>
     * Note that this method always called on a worker thread
     *
     * @param error Exception that has been thrown
     * @see Handler
     *
     * @see Looper.getMainLooper
     * @see Activity.runOnUiThread
     */
    @WorkerThread
    fun onError(error: Exception)

    companion object {
        /**
         * Callback to suppress errors
         */
        @JvmField
        val SUPPRESS: ErrorCallback =
            Utils.SuppressErrorCallback()
    }
}
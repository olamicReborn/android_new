package com.maggnet.utils.codescanner

import androidx.annotation.WorkerThread
import com.google.zxing.Result

/**
 * Callback of the decoding process
 */
interface DecodeCallback {
    /**
     * Called when decoder has successfully decoded the code
     * <br></br>
     * Note that this method always called on a worker thread
     *
     * @param result Encapsulates the result of decoding a barcode within an image
     * @see Handler
     *
     * @see Looper.getMainLooper
     * @see Activity.runOnUiThread
     */
    @WorkerThread
    fun onDecoded(result: Result)
}

package com.maggnet.utils.codescanner

/**
 * Code scanner auto focus mode
 *
 * @see CodeScanner.setAutoFocusMode
 */
enum class AutoFocusMode {
    /**
     * Auto focus camera with the specified interval
     *
     * @see CodeScanner.setAutoFocusInterval
     */
    SAFE,

    /**
     * Continuous auto focus, may not work on some devices
     */
    CONTINUOUS
}
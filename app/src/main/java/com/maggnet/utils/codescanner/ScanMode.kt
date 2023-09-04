package com.maggnet.utils.codescanner

/**
 * Code scanner scan mode
 *
 * @see CodeScanner.setScanMode
 */
enum class ScanMode {
    /**
     * Preview will stop after first decoded code
     */
    SINGLE,

    /**
     * Continuous scan, don't stop preview after decoding the code
     */
    CONTINUOUS,

    /**
     * Preview only, no code recognition
     */
    PREVIEW
}
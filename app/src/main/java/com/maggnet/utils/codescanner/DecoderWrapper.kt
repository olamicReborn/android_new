package com.maggnet.utils.codescanner

import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import com.maggnet.utils.codescanner.Point

internal class DecoderWrapper(
    val camera: Camera,
    val cameraInfo: CameraInfo,
    val decoder: Decoder,
    val imageSize: Point,
    val previewSize: Point,
    val viewSize: Point,
    val displayOrientation: Int,
    autoFocusSupported: Boolean,
    flashSupported: Boolean
) {
    private val mReverseHorizontal: Boolean
    val isAutoFocusSupported: Boolean
    val isFlashSupported: Boolean

    fun shouldReverseHorizontal(): Boolean {
        return mReverseHorizontal
    }

    fun release() {
        camera.release()
        decoder.shutdown()
    }

    init {
        mReverseHorizontal =
            cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT
        isAutoFocusSupported = autoFocusSupported
        isFlashSupported = flashSupported
    }
}
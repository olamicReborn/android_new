package com.maggnet.utils.codescanner

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import com.maggnet.R

internal class ViewFinderView(context: Context) :
    View(context) {
    private val mMaskPaint: Paint
    private val mFramePaint: Paint
    private val mPath: Path
    var frameRect: Rect? = null
        private set
    private var mFrameCornersSize = 0
    private var mFrameCornersRadius = 0
    private var mFrameRatioWidth = 1f
    private var mFrameRatioHeight = 1f
    private var mFrameSize = 0.75f
    override fun onDraw(canvas: Canvas) {
        val frame = frameRect ?: return
        val width = width + 10
        val height = height + 10
        val top = frame.top.toFloat()
        val left = frame.left.toFloat()
        val right = frame.right.toFloat()
        val bottom = frame.bottom.toFloat()
        val frameCornersSize = mFrameCornersSize.toFloat()
        val frameCornersRadius = mFrameCornersRadius.toFloat()
        val path = mPath
        if (frameCornersRadius > 0) {
            val normalizedRadius =
                Math.min(frameCornersRadius, Math.max(frameCornersSize - 1, 0f))
            path.reset()
            path.moveTo(left, top + normalizedRadius)
            path.quadTo(left, top, left + normalizedRadius, top)
            path.lineTo(right - normalizedRadius, top)
            path.quadTo(right, top, right, top + normalizedRadius)
            path.lineTo(right, bottom - normalizedRadius)
            path.quadTo(right, bottom, right - normalizedRadius, bottom)
            path.lineTo(left + normalizedRadius, bottom)
            path.quadTo(left, bottom, left, bottom - normalizedRadius)
            path.lineTo(left, top + normalizedRadius)
            path.moveTo(0f, 0f)
            path.lineTo(width.toFloat(), 0f)
            path.lineTo(width.toFloat(), height.toFloat())
            path.lineTo(0f, height.toFloat())
            path.lineTo(0f, 0f)
            canvas.drawPath(path, mMaskPaint)
            path.reset()
            path.moveTo(left, top + frameCornersSize)
            path.lineTo(left, top + normalizedRadius)
            path.quadTo(left, top, left + normalizedRadius, top)
            path.lineTo(left + frameCornersSize, top)
            path.moveTo(right - frameCornersSize, top)
            path.lineTo(right - normalizedRadius, top)
            path.quadTo(right, top, right, top + normalizedRadius)
            path.lineTo(right, top + frameCornersSize)
            path.moveTo(right, bottom - frameCornersSize)
            path.lineTo(right, bottom - normalizedRadius)
            path.quadTo(right, bottom, right - normalizedRadius, bottom)
            path.lineTo(right - frameCornersSize, bottom)
            path.moveTo(left + frameCornersSize, bottom)
            path.lineTo(left + normalizedRadius, bottom)
            path.quadTo(left, bottom, left, bottom - normalizedRadius)
            path.lineTo(left, bottom - frameCornersSize)
            canvas.drawPath(path, mFramePaint)
        } else {
            path.reset()
            path.moveTo(left, top)
            path.lineTo(right, top)
            path.lineTo(right, bottom)
            path.lineTo(left, bottom)
            path.lineTo(left, top)
            path.moveTo(0f, 0f)
            path.lineTo(width.toFloat(), 0f)
            path.lineTo(width.toFloat(), height.toFloat())
            path.lineTo(0f, height.toFloat())
            path.lineTo(0f, 0f)
            canvas.drawPath(path, mMaskPaint)
            path.reset()
            path.moveTo(left, top + frameCornersSize)
            path.lineTo(left, top)
            path.lineTo(left + frameCornersSize, top)
            path.moveTo(right - frameCornersSize, top)
            path.lineTo(right, top)
            path.lineTo(right, top + frameCornersSize)
            path.moveTo(right, bottom - frameCornersSize)
            path.lineTo(right, bottom)
            path.lineTo(right - frameCornersSize, bottom)
            path.moveTo(left + frameCornersSize, bottom)
            path.lineTo(left, bottom)
            path.lineTo(left, bottom - frameCornersSize)
            canvas.drawPath(path, mFramePaint)
        }
    }

    override fun onLayout(
        changed: Boolean, left: Int, top: Int, right: Int,
        bottom: Int
    ) {
        invalidateFrameRect(right - left, bottom - top)
    }

    fun setFrameAspectRatio(
        @FloatRange(from = 0.0, fromInclusive = false) ratioWidth: Float,
        @FloatRange(from = 0.0, fromInclusive = false) ratioHeight: Float
    ) {
        mFrameRatioWidth = ratioWidth
        mFrameRatioHeight = ratioHeight
        invalidateFrameRect()
        if (isLaidOut) {
            invalidate()
        }
    }

    @get:FloatRange(from = 0.0, fromInclusive = false)
    var frameAspectRatioWidth: Float
        get() = mFrameRatioWidth
        set(ratioWidth) {
            mFrameRatioWidth = ratioWidth
            invalidateFrameRect()
            if (isLaidOut) {
                invalidate()
            }
        }

    @get:FloatRange(from = 0.0, fromInclusive = false)
    var frameAspectRatioHeight: Float
        get() = mFrameRatioHeight
        set(ratioHeight) {
            mFrameRatioHeight = ratioHeight
            invalidateFrameRect()
            if (isLaidOut) {
                invalidate()
            }
        }

    @get:ColorInt
    var maskColor: Int
        get() = mMaskPaint.color
        set(color) {
            mMaskPaint.color = color
            if (isLaidOut) {
                invalidate()
            }
        }

    //mFramePaint.setColor(color);
    @get:ColorInt
    var frameColor: Int
        get() = mFramePaint.color
        set(color) {
            //mFramePaint.setColor(color);
            if (isLaidOut) {
                invalidate()
            }
        }

    @get:Px
    var frameThickness: Int
        get() = mFramePaint.strokeWidth.toInt()
        set(thickness) {
            mFramePaint.strokeWidth = thickness.toFloat()
            if (isLaidOut) {
                invalidate()
            }
        }

    @get:Px
    var frameCornersSize: Int
        get() = mFrameCornersSize
        set(size) {
            mFrameCornersSize = size
            if (isLaidOut) {
                invalidate()
            }
        }

    @get:Px
    var frameCornersRadius: Int
        get() = mFrameCornersRadius
        set(radius) {
            mFrameCornersRadius = radius
            if (isLaidOut) {
                invalidate()
            }
        }

    @get:FloatRange(from = 0.1, to = 1.0)
    var frameSize: Float
        get() = mFrameSize
        set(size) {
            mFrameSize = size
            invalidateFrameRect()
            if (isLaidOut) {
                invalidate()
            }
        }

    private fun invalidateFrameRect(
        width: Int = getWidth(),
        height: Int = getHeight()
    ) {
        if (width > 0 && height > 0) {
            val viewAR = width.toFloat() / height.toFloat()
            val frameAR = mFrameRatioWidth / mFrameRatioHeight
            val frameWidth: Int
            val frameHeight: Int
            if (viewAR <= frameAR) {
                frameWidth = Math.round(width * mFrameSize)
                frameHeight = Math.round(frameWidth / frameAR)
            } else {
                frameHeight = Math.round(height * mFrameSize)
                frameWidth = Math.round(frameHeight * frameAR)
            }
            val frameLeft = (width - frameWidth) / 2
            val frameTop = (height - frameHeight) / 3
            frameRect = Rect(
                frameLeft,
                frameTop,
                frameLeft + frameWidth,
                frameTop + frameHeight
            )
        }
    }

    init {
        mMaskPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mMaskPaint.style = Paint.Style.FILL
        mFramePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mFramePaint.style = Paint.Style.STROKE
        mFramePaint.color = ContextCompat.getColor(
            context,
            R.color.blue
        )
        val path = Path()
        path.setFillType(Path.FillType.EVEN_ODD)
        mPath = path
    }
}
package com.example.taskmanager.util

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RotateDrawable

class CircleProgressDrawable : Drawable() {
    private val primaryCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val secondaryCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val outerCircleRectF = RectF()
    private val innerCircleRectF = RectF()

    init {
        primaryCirclePaint.color = Color.LTGRAY
        secondaryCirclePaint.color = Color.RED
        secondaryCirclePaint.style = Paint.Style.STROKE
    }

    private val layerDrawable: LayerDrawable by lazy {
        LayerDrawable(arrayOf(
            ColorDrawable(Color.GRAY),
            RotateDrawable(),
            GradientDrawable()
        ))
    }
    fun createLayerDrawable(): LayerDrawable {
        return layerDrawable
    }

    override fun draw(canvas: Canvas) {
        val bounds = bounds
        outerCircleRectF.set(bounds)
        innerCircleRectF.set(outerCircleRectF.left + 5, outerCircleRectF.top + 5, outerCircleRectF.right - 5, outerCircleRectF.bottom - 5)

        canvas.drawCircle(outerCircleRectF.centerX(), outerCircleRectF.centerY(), outerCircleRectF.width() / 2, primaryCirclePaint)
        canvas.drawArc(innerCircleRectF, -90f, 360f, false, secondaryCirclePaint)
    }

    override fun setAlpha(alpha: Int) {
        primaryCirclePaint.alpha = alpha
        secondaryCirclePaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        primaryCirclePaint.colorFilter = colorFilter
        secondaryCirclePaint.colorFilter = colorFilter
    }

    fun setProgress(percentage: Double) {
        secondaryCirclePaint.strokeWidth = (100f / 100 * percentage).toFloat()
        invalidateSelf()
    }
}
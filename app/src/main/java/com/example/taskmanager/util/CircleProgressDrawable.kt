package com.example.taskmanager.util

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable


class CircleProgressDrawable : Drawable() {
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val rectF = RectF()
    private var progress = 0.0 // Percentage of progress

    init {
        backgroundPaint.color = Color.RED // Red circle color
        progressPaint.color = Color.parseColor("#228B22") // Green circle color
        progressPaint.style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
        val bounds = bounds
        rectF.set(bounds)

        val centerX = rectF.centerX()
        val centerY = rectF.centerY()
        val radius = (Math.min(rectF.width(), rectF.height()) / 2).toFloat()

        // Draw the background circle (red)
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint)

        // Draw the progress circle (green) based on the percentage
        val sweepAngle = (360 * progress).toFloat()
        val oval = RectF(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )
        canvas.drawArc(oval, -90f, sweepAngle, true, progressPaint)
    }

    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    fun setProgress(percentage: Double) {
        progress = percentage / 100 // Convert percentage to a fraction
        invalidateSelf() // Refresh the drawable
    }
}

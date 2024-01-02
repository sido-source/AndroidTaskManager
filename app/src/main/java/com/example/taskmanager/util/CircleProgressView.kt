package com.example.taskmanager.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CircleProgressImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    private val backgroundPaint = Paint().apply {
        color = Color.GREEN // Set the background color to green
        style = Paint.Style.FILL
    }

    private val progressPaint = Paint().apply {
        color = Color.RED // Set the progress color to red
        style = Paint.Style.FILL
    }

    private var percentageOfDone: Double = 0.0 // Initialize with default value

    fun setPercentageOfDone(percentage: Double) {
        this.percentageOfDone = percentage
        invalidate() // Request a redraw
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2.toFloat()
        val centerY = height / 2.toFloat()
        val radius = width.coerceAtMost(height) / 2.toFloat()

        // Draw green background circle
        canvas?.drawCircle(centerX, centerY, radius, backgroundPaint)

        // Draw red progress arc based on the percentage
        val oval = RectF(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )
        canvas?.drawArc(
            oval,
            START_ANGLE,
            (percentageOfDone * 360).toFloat(),
            true,
            progressPaint
        )
    }

    companion object {
        private const val START_ANGLE = -90f // Start angle for the progress arc
    }
}
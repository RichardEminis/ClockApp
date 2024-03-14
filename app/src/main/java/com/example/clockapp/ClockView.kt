package com.example.clockapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class ClockView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val textPaint: Paint = Paint()
    private val circleFramePaint: Paint = Paint()
    private val circleMainPaint: Paint = Paint()
    private val circleCenterPaint: Paint = Paint()
    private var textSize: Float = 0f
    private var radius: Float = 0f


    init {
        circleFramePaint.color = Color.BLUE
        circleMainPaint.color = Color.GREEN
        circleCenterPaint.color = Color.RED
        textPaint.color = Color.BLACK
        textPaint.textSize = 70f
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        textSize = (w.coerceAtMost(h) / 10).toFloat()
        textPaint.textSize = textSize

        radius = w.coerceAtMost(h) / 2.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width
        val height = height
        val radius = width.coerceAtMost(height) / 2

        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            radius.toFloat(),
            circleFramePaint
        )
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            radius.toFloat() - radius/15,
            circleMainPaint
        )
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            radius.toFloat() /30,
            circleCenterPaint
        )


        val centerX = width / 2
        val centerY = height / 2

        val angleStep = 2 * Math.PI / 12

        val textRadius = radius - (radius/5)

        for (i in 1..12) {
            val angle = i * angleStep - Math.PI / 2
            val x = (centerX + textRadius * cos(angle)).toFloat()
            val y = (centerY + textRadius * sin(angle)).toFloat()

            val textHeight = textPaint.descent() - textPaint.ascent()

            canvas.drawText(i.toString(), x, y - textHeight / 2 - textPaint.ascent(), textPaint)
        }
    }
}
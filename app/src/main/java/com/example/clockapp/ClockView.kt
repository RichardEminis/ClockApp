package com.example.clockapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin

class ClockView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val textPaint: Paint = Paint()
    private val circleFramePaint: Paint = Paint()
    private val circleMainPaint: Paint = Paint()
    private val circleCenterPaint: Paint = Paint()
    private val hourHandPaint: Paint = Paint()
    private val minuteHandPaint: Paint = Paint()
    private val secondHandPaint: Paint = Paint()

    private var textSize: Float = 0f
    private var radius: Float = 0f
    private var centerX: Float = 0f
    private var centerY: Float = 0f

    private var hourHandLength: Float = 0f
    private var minuteHandLength: Float = 0f
    private var secondHandLength: Float = 0f

    private var hourHandAngle: Double = 0.0
    private var minuteHandAngle: Double = 0.0
    private var secondHandAngle: Double = 0.0

    init {
        circleFramePaint.color = Color.BLUE
        circleMainPaint.color = Color.GREEN
        circleCenterPaint.color = Color.RED
        textPaint.color = Color.BLACK
        textPaint.textSize = 70f
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

        hourHandPaint.color = Color.BLACK
        hourHandPaint.strokeWidth = 10f
        hourHandPaint.strokeCap = Paint.Cap.ROUND

        minuteHandPaint.color = Color.BLACK
        minuteHandPaint.strokeWidth = 6f
        minuteHandPaint.strokeCap = Paint.Cap.ROUND

        secondHandPaint.color = Color.RED
        secondHandPaint.strokeWidth = 3f
        secondHandPaint.strokeCap = Paint.Cap.ROUND
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        textSize = (w.coerceAtMost(h) / 10).toFloat()
        textPaint.textSize = textSize

        centerX = w / 2f
        centerY = h / 2f
        radius = w.coerceAtMost(h) / 2.toFloat()

        hourHandLength = radius * 0.5f
        minuteHandLength = radius * 0.7f
        secondHandLength = radius * 0.8f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY) % 12
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        hourHandAngle = Math.toRadians((hour * 30 + minute / 2).toDouble())
        minuteHandAngle = Math.toRadians((minute * 6).toDouble())
        secondHandAngle = Math.toRadians((second * 6).toDouble())

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
            radius.toFloat() - radius / 15,
            circleMainPaint
        )
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            radius.toFloat() / 30,
            circleCenterPaint
        )

        canvas.drawLine(
            centerX, centerY,
            (centerX + hourHandLength * sin(hourHandAngle)).toFloat(),
            (centerY - hourHandLength * cos(hourHandAngle)).toFloat(),
            minuteHandPaint
        )
        canvas.drawLine(
            centerX, centerY,
            (centerX + minuteHandLength * sin(minuteHandAngle)).toFloat(),
            (centerY - minuteHandLength * cos(minuteHandAngle)).toFloat(),
            minuteHandPaint
        )
        canvas.drawLine(
            centerX, centerY,
            (centerX + secondHandLength * sin(secondHandAngle)).toFloat(),
            (centerY - secondHandLength * cos(secondHandAngle)).toFloat(),
            minuteHandPaint
        )

        val centerX = width / 2
        val centerY = height / 2

        val angleStep = 2 * Math.PI / 12

        val textRadius = radius - (radius / 5)

        for (i in 1..12) {
            val angle = i * angleStep - Math.PI / 2
            val x = (centerX + textRadius * cos(angle)).toFloat()
            val y = (centerY + textRadius * sin(angle)).toFloat()

            val textHeight = textPaint.descent() - textPaint.ascent()

            canvas.drawText(i.toString(), x, y - textHeight / 2 - textPaint.ascent(), textPaint)
        }

        postInvalidateDelayed(1000)
    }
}
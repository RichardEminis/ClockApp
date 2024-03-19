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

    private var currentHour: Int = 0
    private var currentMinute: Int = 0
    private var currentSecond: Int = 0

    private val textPaint: Paint = Paint()
    private val circleFramePaint: Paint = Paint()
    private val circleMainPaint: Paint = Paint()
    private val circleCenterPaint: Paint = Paint()
    private val hourHandPaint: Paint = Paint()
    private val minuteHandPaint: Paint = Paint()
    private val secondHandPaint: Paint = Paint()
    private val pointsPaint: Paint = Paint()

    private var textSize: Float = 0f
    private var radius: Float = 0f
    private var centerX: Float = 0f
    private var centerY: Float = 0f

    private var hourHandLength: Float = 0f
    private var minuteHandLength: Float = 0f
    private var secondHandLength: Float = 0f

    init {
        circleFramePaint.color = Color.DKGRAY
        circleMainPaint.color = Color.LTGRAY
        circleCenterPaint.color = Color.RED

        textPaint.color = Color.BLACK
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

        hourHandPaint.color = Color.BLACK
        hourHandPaint.strokeWidth = 20f
        hourHandPaint.strokeCap = Paint.Cap.ROUND

        minuteHandPaint.color = Color.MAGENTA
        minuteHandPaint.strokeWidth = 13f
        minuteHandPaint.strokeCap = Paint.Cap.ROUND

        secondHandPaint.color = Color.RED
        secondHandPaint.strokeWidth = 8f
        secondHandPaint.strokeCap = Paint.Cap.ROUND

        pointsPaint.color = Color.BLACK
        pointsPaint.strokeWidth = 5f
        pointsPaint.style = Paint.Style.FILL
        pointsPaint.isAntiAlias = true
    }

    fun setTime(hour: Int, minute: Int, second: Int) {
        currentHour = hour
        currentMinute = minute
        currentSecond = second
        invalidate()
    }

    fun setCircleMainColor(color: Int) {
        circleMainPaint.color = color
        invalidate()
    }

    fun setSecondHandColor(color: Int) {
        secondHandPaint.color = color
        invalidate()
    }

    fun setCircleFrameColor(color: Int) {
        circleFramePaint.color = color
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        textSize = (w.coerceAtMost(h) / 8).toFloat()
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

        val hourHandAngle = Math.toRadians((currentHour % 12 * 30 + currentMinute / 2).toDouble())
        val minuteHandAngle = Math.toRadians((currentMinute * 6).toDouble())
        val secondHandAngle = Math.toRadians((currentSecond * 6).toDouble())

        canvas.drawCircle(centerX, centerY, radius, circleFramePaint)
        canvas.drawCircle(centerX, centerY, radius - radius / 15, circleMainPaint)
        canvas.drawCircle(centerX, centerY, radius / 30, circleCenterPaint)

        val radiusOfPoints = radius / 2.3
        val textRadius = radius - (radius / 3)

        for (i in 1..12) {
            val angleStep = 2 * Math.PI / 12
            val angle = i * angleStep - Math.PI / 2
            val x = (centerX + textRadius * cos(angle)).toFloat()
            val y = (centerY + textRadius * sin(angle)).toFloat()

            val textHeight = textPaint.descent() - textPaint.ascent()

            canvas.drawText(i.toString(), x, y - textHeight / 2 - textPaint.ascent(), textPaint)
        }

        for (i in 0 until 60) {
            val angleStep = 2 * Math.PI / 60
            val angle = i * angleStep

            val x = (centerX + radiusOfPoints * cos(angle)).toFloat()
            val y = (centerY + radiusOfPoints * sin(angle)).toFloat()

            val pointRadius = if (i % 5 == 0) 7f else 5f
            canvas.drawCircle(x, y, pointRadius, pointsPaint)
        }

        canvas.drawLine(
            centerX, centerY,
            (centerX + hourHandLength * sin(hourHandAngle)).toFloat(),
            (centerY - hourHandLength * cos(hourHandAngle)).toFloat(),
            hourHandPaint
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
            secondHandPaint
        )
    }
}
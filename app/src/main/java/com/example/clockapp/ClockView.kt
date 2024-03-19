package com.example.clockapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import java.util.Calendar
import java.util.TimeZone
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

    private var timeZone: String = TimeZone.getDefault().id

    init {
        circleFramePaint.color = Color.DKGRAY
        circleMainPaint.color = Color.LTGRAY
        circleCenterPaint.color = Color.RED

        textPaint.color = Color.BLACK
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

        hourHandPaint.color = Color.BLACK
        hourHandPaint.strokeWidth = HOUR_HAND_WIDTH
        hourHandPaint.strokeCap = Paint.Cap.ROUND

        minuteHandPaint.color = Color.MAGENTA
        minuteHandPaint.strokeWidth = MINUTE_HAND_WIDTH
        minuteHandPaint.strokeCap = Paint.Cap.ROUND

        secondHandPaint.color = Color.RED
        secondHandPaint.strokeWidth = SECOND_HAND_WIDTH
        secondHandPaint.strokeCap = Paint.Cap.ROUND

        pointsPaint.color = Color.BLACK
        pointsPaint.strokeWidth = POINTS_RADIUS_SMALL
        pointsPaint.style = Paint.Style.FILL
        pointsPaint.isAntiAlias = true
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

    fun setTimeZone(timeZone: String) {
        this.timeZone = timeZone
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        textSize = (w.coerceAtMost(h) / TEXT_SIZE_RATIO).toFloat()
        textPaint.textSize = textSize

        centerX = w / 2f
        centerY = h / 2f
        radius = w.coerceAtMost(h) / 2.toFloat()

        hourHandLength = radius * HOUR_HAND_RATIO
        minuteHandLength = radius * MINUTE_HAND_RATIO
        secondHandLength = radius * SECOND_HAND_RATIO
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone))
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        val hourHandAngle =
            Math.toRadians((hour % HOURS_IN_CLOCK * DEGREES_PER_HOUR + minute / 2).toDouble())
        val minuteHandAngle = Math.toRadians((minute * DEGREES_PER_MINUTE).toDouble())
        val secondHandAngle = Math.toRadians((second * DEGREES_PER_MINUTE).toDouble())

        canvas.drawCircle(centerX, centerY, radius, circleFramePaint)
        canvas.drawCircle(centerX, centerY, radius - radius / FRAME_CIRCLE_RATIO, circleMainPaint)
        canvas.drawCircle(centerX, centerY, radius / DEGREES_PER_HOUR, circleCenterPaint)

        val radiusOfPoints = radius / POINTS_RADIUS_RATIO
        val textRadius = radius - (radius / TEXT_RADIUS_RATIO)

        for (i in 1..HOURS_IN_CLOCK) {
            val angleStep = 2 * Math.PI / HOURS_IN_CLOCK
            val angle = i * angleStep - Math.PI / 2
            val x = (centerX + textRadius * cos(angle)).toFloat()
            val y = (centerY + textRadius * sin(angle)).toFloat()

            val textHeight = textPaint.descent() - textPaint.ascent()

            canvas.drawText(i.toString(), x, y - textHeight / 2 - textPaint.ascent(), textPaint)
        }

        for (i in 0 until MINUTES_IN_HOUR) {
            val angleStep = 2 * Math.PI / MINUTES_IN_HOUR
            val angle = i * angleStep

            val x = (centerX + radiusOfPoints * cos(angle)).toFloat()
            val y = (centerY + radiusOfPoints * sin(angle)).toFloat()

            val pointRadius = if (i % 5 == 0) POINTS_RADIUS_LARGE else POINTS_RADIUS_SMALL
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

        postInvalidateDelayed(SECOND_DELAY)
    }
}
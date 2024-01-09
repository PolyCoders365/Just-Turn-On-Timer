package jtot.dev.feature.timertodo.timerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import jtot.dev.R
import jtot.dev.utils.dpToPixel
import kotlin.math.cos
import kotlin.math.sin

/**
 * 원 각도 기준
 *
 * 0 - right
 *
 * 90 - bottom
 *
 * 180- left
 *
 * 270- top
 */

class TimerView
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : View(context, attrs, defStyleAttr) {
        private val leftPaint =
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL_AND_STROKE
                color = ContextCompat.getColor(context, R.color.red)
            }
        private val circlePaint =
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL_AND_STROKE
                color = ContextCompat.getColor(context, R.color.tertiary95)
            }
        private val centerCirclePaint =
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL_AND_STROKE
                color = ContextCompat.getColor(context, R.color.white)
                strokeWidth = 10F
            }
        private val textPaint =
            TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
                color = ContextCompat.getColor(context, R.color.black)
                textSize = context.dpToPixel(20F)
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                textAlign = Paint.Align.CENTER
            }
        private val linePaint =
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL_AND_STROKE
                color = ContextCompat.getColor(context, R.color.black)
                strokeWidth = 3F
            }

        private val circleMargin = context.dpToPixel(48F)

        private var endTime = 0
        private var isDrawMinutesText = false
        private val angleList =
            listOf(
                Pair(0, 0),
                Pair(30, 5),
                Pair(60, 10),
                Pair(90, 15),
                Pair(120, 20),
                Pair(150, 25),
                Pair(180, 30),
                Pair(210, 35),
                Pair(240, 40),
                Pair(270, 45),
                Pair(300, 50),
                Pair(330, 55),
            )
        private val centerCircle: RectF by lazy {
            RectF().apply {
                set(
                    width.toFloat() / 2 - context.dpToPixel(8f),
                    width.toFloat() / 2 - context.dpToPixel(8f),
                    width.toFloat() / 2 + context.dpToPixel(8f),
                    width.toFloat() / 2 + context.dpToPixel(8f),
                )
            }
        }
        private val circle: RectF by lazy {
            RectF().apply {
                set(
                    circleMargin,
                    circleMargin,
                    width.toFloat() - circleMargin,
                    width.toFloat() - circleMargin,
                )
            }
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            canvas.run {
                if (!isDrawMinutesText) {
                    drawInitBackground(circleRectF = circle, centerRectF = centerCircle)
                }

                // 설정된 endTime으로 빨간색 호를 그림
                drawArc(circle, 270f, -(endTime * 6).toFloat(), true, leftPaint)
            }
        }

        private fun Canvas.drawInitBackground(
            circleRectF: RectF,
            centerRectF: RectF,
        ) {
            angleList.forEach { pair ->
                val angle = getAngle(pair.first)
                val point =
                    getPoint(
                        angle,
                        width.toFloat() / 2,
                        context.dpToPixel(16f),
                        width.toFloat() / 2,
                        width.toFloat() / 2,
                    )
                // 5분 단위 선 그림
                drawLine(
                    point.first,
                    point.second,
                    width.toFloat() / 2,
                    width.toFloat() / 2,
                    linePaint,
                )

                // Text background 그림
                drawRect(
                    point.first - context.dpToPixel(16f),
                    point.second - context.dpToPixel(16f),
                    point.first + context.dpToPixel(16f),
                    point.second + context.dpToPixel(16f),
                    Paint().apply { color = Color.WHITE },
                )
                // 분 Text 그림
                drawText(
                    pair.second.toString(),
                    point.first,
                    point.second + context.dpToPixel(8f),
                    textPaint,
                )
            }

            // 배경 원 그림
            drawArc(circleRectF, 270f, 360f, true, circlePaint)

            // 가운데 작은 원 그림
            drawArc(centerRectF, 270f, 360f, true, centerCirclePaint)
        }

        fun setTime(minute: Int) {
            endTime = minute
            invalidate()
        }

        private fun getAngle(angle: Int) = (angle * Math.PI / 180).toFloat()

        private fun getPoint(
            angle: Float,
            startXPoint: Float,
            startYPoint: Float,
            centerXPoint: Float,
            centerYPoint: Float,
        ): Pair<Float, Float> {
            val vectorAX = centerXPoint - startXPoint
            val vectorAY = centerYPoint - startYPoint

            val aX = vectorAX * cos(angle) - vectorAY * sin(angle)
            val aY = vectorAX * sin(angle) - vectorAY * cos(angle)
            val cX =
                centerXPoint + aX
            val cY =
                centerYPoint + aY
            return Pair(cX, cY)
        }
    }

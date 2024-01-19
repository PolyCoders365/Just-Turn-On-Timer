package jtot.dev.feature.timertodo.timerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
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
            getDefaultPaint().apply {
                color = ContextCompat.getColor(context, R.color.red)
            }

        private val circlePaint =
            getDefaultPaint().apply {
                color = ContextCompat.getColor(context, R.color.tertiary90)
            }

        private val centerCirclePaint =
            getDefaultPaint().apply {
                color = ContextCompat.getColor(context, R.color.white)
                strokeWidth = 10F
            }

        private val textPaint =
            getDefaultPaint().apply {
                color = ContextCompat.getColor(context, R.color.black)
                textSize = context.dpToPixel(20F)
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                textAlign = Paint.Align.CENTER
            }
        private val fiveMinLinePaint =
            getDefaultPaint().apply {
                color = ContextCompat.getColor(context, R.color.black)
                strokeWidth = 3F
            }
        private val minLinePaint =
            getDefaultPaint().apply {
                color = ContextCompat.getColor(context, R.color.black)
                strokeWidth = 1F
            }

        private val currentLinePaint =
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                isAntiAlias = true
                strokeJoin = Paint.Join.BEVEL
                style = Paint.Style.STROKE
                color = ContextCompat.getColor(context, R.color.white)
                strokeWidth = 20F
            }

        private val circleMargin = context.dpToPixel(48F)
        private val fiveMinTextMargin = context.dpToPixel(12f)

        private val centerCircleMargin = context.dpToPixel(8f)

        private var endTime = 0
        private var isDrawMinutesText = false

        private val minAngleList =
            mutableListOf<Pair<Int, Int>>().apply {
                repeat(60) { num ->
                    this.add(Pair(6 * num, num))
                }
            }
        private val centerCircle: RectF by lazy {
            RectF().apply {
                set(
                    width.toFloat() / 2 - centerCircleMargin,
                    width.toFloat() / 2 - centerCircleMargin,
                    width.toFloat() / 2 + centerCircleMargin,
                    width.toFloat() / 2 + centerCircleMargin,
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
                    drawInitBackground(circleRectF = circle)
                }

                // 설정된 endTime으로 빨간색 호를 그림
                drawArc(circle, 270f, -(endTime * 0.1).toFloat(), true, leftPaint)
                val currentAngle = getAngle(endTime * 0.1)
                val currentPoint =
                    getPoint(
                        currentAngle,
                        (width.toFloat() / 2),
                        (width.toFloat() / 2) - context.dpToPixel(32f),
                        width.toFloat() / 2,
                        width.toFloat() / 2,
                    )
                // 현재 분침
                drawLine(
                    currentPoint.first,
                    currentPoint.second,
                    width.toFloat() / 2,
                    width.toFloat() / 2,
                    currentLinePaint,
                )
                // 가운데 작은 원
                drawArc(centerCircle, 270f, 360f, true, centerCirclePaint)
            }
        }

        private fun Canvas.drawInitBackground(circleRectF: RectF) {
            // 분 선
            minAngleList.forEach { pair ->
                val angle = getAngle(pair.first.toDouble())
                val fiveMinPoint =
                    getPoint(
                        angle,
                        width.toFloat() / 2,
                        context.dpToPixel(16f),
                        width.toFloat() / 2,
                        width.toFloat() / 2,
                    )
                val minPoint =
                    getPoint(
                        angle,
                        (width.toFloat() / 2),
                        context.dpToPixel(36f),
                        width.toFloat() / 2,
                        width.toFloat() / 2,
                    )

                if (pair.second % 5 == 0) {
                    // 5분 단위 선 그림
                    drawLine(
                        fiveMinPoint.first,
                        fiveMinPoint.second,
                        width.toFloat() / 2,
                        width.toFloat() / 2,
                        fiveMinLinePaint,
                    )
                } else {
                    // 1분 단위 선 그림
                    drawLine(
                        minPoint.first,
                        minPoint.second,
                        width.toFloat() / 2,
                        width.toFloat() / 2,
                        minLinePaint,
                    )
                }
                // Text background
                drawRect(
                    fiveMinPoint.first - fiveMinTextMargin,
                    fiveMinPoint.second - fiveMinTextMargin,
                    fiveMinPoint.first + fiveMinTextMargin,
                    fiveMinPoint.second + fiveMinTextMargin,
                    Paint().apply { color = Color.WHITE },
                )
            }

            // text
            minAngleList.forEach { pair ->
                val angle = getAngle(pair.first.toDouble())
                val fiveMinPoint =

                    getPoint(
                        angle,
                        width.toFloat() / 2,
                        context.dpToPixel(16f),
                        width.toFloat() / 2,
                        width.toFloat() / 2,
                    )
                if (pair.second % 5 == 0) {
                    drawText(
                        pair.second.toString(),
                        fiveMinPoint.first,
                        fiveMinPoint.second + context.dpToPixel(8f),
                        textPaint,
                    )
                }
            }

            // 배경 원
            drawArc(circleRectF, 270f, 360f, true, circlePaint)
        }

        fun setTime(totalSecond: Int) {
            endTime = totalSecond
            invalidate()
        }

        private fun getAngle(angle: Double) = (angle * Math.PI / 180).toFloat()

        private fun getPoint(
            angle: Float,
            startXPoint: Float,
            startYPoint: Float,
            centerXPoint: Float,
            centerYPoint: Float,
        ): Pair<Float, Float> {
            val vectorX = centerXPoint - startXPoint
            val vectorY = centerYPoint - startYPoint

            val pointVectorX = vectorX * cos(angle) - vectorY * sin(angle)
            val pointVectorY = vectorX * sin(angle) - vectorY * cos(angle)
            val pointX =
                centerXPoint + pointVectorX
            val pointY =
                centerYPoint + pointVectorY
            return Pair(pointX, pointY)
        }

        private fun getDefaultPaint(): Paint {
            return Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL_AND_STROKE
            }
        }
    }

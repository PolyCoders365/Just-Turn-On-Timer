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
                color = ContextCompat.getColor(context, R.color.tertiary95)
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
        private val linePaint =
            getDefaultPaint().apply {
                color = ContextCompat.getColor(context, R.color.black)
                strokeWidth = 3F
            }

        private val circleMargin = context.dpToPixel(48F)
        private val textMargin = context.dpToPixel(16f)
        private val centerCircleMargin = context.dpToPixel(8f)

        private var endTime = 0
        private var isDrawMinutesText = false
        private val angleList =
            mutableListOf<Pair<Int, Int>>().apply {
                repeat(12) { num ->
                    this.add(Pair(30 * num, 5 * num))
                }
            }.toList()
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

                // Text background
                drawRect(
                    point.first - textMargin,
                    point.second - textMargin,
                    point.first + textMargin,
                    point.second + textMargin,
                    Paint().apply { color = Color.WHITE },
                )
                // 분 Text
                drawText(
                    pair.second.toString(),
                    point.first,
                    point.second + context.dpToPixel(8f),
                    textPaint,
                )
            }

            // 배경 원
            drawArc(circleRectF, 270f, 360f, true, circlePaint)

            // 가운데 작은 원
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

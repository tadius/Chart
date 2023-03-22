package com.tadiuzzz.chart.presentation.chartScreen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tadiuzzz.chart.domain.model.Point

@Composable
fun Chart(
    modifier: Modifier = Modifier,
    points: List<Point>,
    pointsColors: List<Color>,
    onChartAction: (isTouching: Boolean) -> Unit,
    onScrollChange: (xOffset: Float, yOffset: Float) -> Unit,
    onScaleChange: (scaleFactor: Float) -> Unit,
    scrollX: Float,
    scrollY: Float,
    scale: Float,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        awaitFirstDown()
                        onChartAction(true)
                        do {
                            val event = awaitPointerEvent()
                            onScaleChange(
                                event.calculateZoom()
                            )
                            val offset = event.calculatePan()
                            onScrollChange(offset.x, offset.y)
                        } while (event.changes.any { it.pressed })
                        onChartAction(false)
                    }
                }
            }
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
        ) {

            val canvasWidth = size.width
            val canvasHeight = size.height
            val center = Point((canvasWidth / 2f) + scrollX, (canvasHeight / 2f) + scrollY)

            drawRect(
                color = Color.Black,
                topLeft = Offset.Zero,
                size = Size(
                    width = canvasWidth,
                    height = canvasHeight
                ),
                style = Stroke()
            )

            //Рисуем вертикальные линии
            var offsetXPositive = scale * 10
            var offsetXNegative = scale * 10
            val linesCountXPositive = (canvasWidth - (canvasWidth - center.x)) / offsetXPositive
            val linesCountXNegative = (canvasWidth - center.x) / offsetXNegative
            for (i in 1..linesCountXPositive.toInt()) {
                if (center.x - offsetXPositive < canvasWidth) {
                    drawLine(
                        start = Offset(x = center.x - offsetXPositive, y = 0f),
                        end = Offset(x = center.x - offsetXPositive, y = canvasHeight),
                        color = Color.LightGray,
                        strokeWidth = 1F
                    )
                }
                offsetXPositive = i * scale * 10
            }
            for (i in 1..linesCountXNegative.toInt()) {
                if (center.x + offsetXNegative > 0) {
                    drawLine(
                        start = Offset(x = center.x + offsetXNegative, y = 0f),
                        end = Offset(x = center.x + offsetXNegative, y = canvasHeight),
                        color = Color.LightGray,
                        strokeWidth = 1F
                    )
                }
                offsetXNegative = i * scale * 10
            }

            //Рисуем горизонтальные линии
            var offsetYPositive = scale * 10
            var offsetYNegative = scale * 10
            val linesCountYPositive = (canvasHeight - (canvasHeight - center.y)) / offsetYPositive
            val linesCountYNegative = (canvasHeight - center.y) / offsetYNegative
            for (i in 1..linesCountYPositive.toInt()) {
                if (center.y - offsetYPositive < canvasHeight) {
                    drawLine(
                        start = Offset(x = 0f, y = center.y - offsetYPositive),
                        end = Offset(x = canvasWidth, y = center.y - offsetYPositive),
                        color = Color.LightGray,
                        strokeWidth = 1F
                    )
                }
                offsetYPositive = i * scale * 10
            }
            for (i in 1..linesCountYNegative.toInt()) {
                if (center.y + offsetYNegative > 0) {
                    drawLine(
                        start = Offset(x = 0f, y = center.y + offsetYNegative),
                        end = Offset(x = canvasWidth, y = center.y + offsetYNegative),
                        color = Color.LightGray,
                        strokeWidth = 1F
                    )
                }
                offsetYNegative = i * scale * 10
            }

            //Рисуем центр координат Х
            if (center.x < canvasWidth && center.x > 0) {
                drawLine(
                    start = Offset(x = center.x, y = 0f),
                    end = Offset(x = center.x, y = canvasHeight),
                    color = Color.Red,
                    strokeWidth = 3F
                )
            }

            //Рисуем центр координат Y
            if (center.y < canvasHeight && center.y > 0) {
                drawLine(
                    start = Offset(x = 0f, y = center.y),
                    end = Offset(x = canvasWidth, y = center.y),
                    color = Color.Red,
                    strokeWidth = 3F
                )
            }

            //Рисуем линию
            val controlPoints1 = mutableListOf<Point>()
            val controlPoints2 = mutableListOf<Point>()
            for (i in 1 until points.size) {
                controlPoints1.add(Point((points[i].x + points[i - 1].x) / 2, points[i - 1].y))
                controlPoints2.add(Point((points[i].x + points[i - 1].x) / 2, points[i].y))
            }
            val stroke = Path().apply {
                reset()
                moveTo(
                    center.x + points.first().x * scale,
                    center.y - points.first().y * scale
                )
                for (i in 0 until points.size - 1) {

                    val x1 = center.x + controlPoints1[i].x * scale
                    val y1 = center.y - controlPoints1[i].y * scale
                    val x2 = center.x + controlPoints2[i].x * scale
                    val y2 = center.y - controlPoints2[i].y * scale

                    val x3 = center.x + points[i + 1].x * scale
                    val y3 = center.y - points[i + 1].y * scale

                    cubicTo(
                        x1,
                        y1,
                        x2,
                        y2,
                        x3,
                        y3
                    )
                }
            }
            drawPath(
                stroke,
                color = Color.Black,
                style = Stroke(
                    width = 5f,
                    cap = StrokeCap.Round
                )
            )

            //Рисуем точки
            points.forEachIndexed { index, point ->
                val x = center.x + point.x * scale
                val y = center.y - point.y * scale
                if (
                    x < canvasWidth && x > 0 &&
                    y < canvasHeight && y > 0
                ) {
                    drawCircle(
                        color = pointsColors[index],
                        radius = 2.dp.toPx(),
                        center = Offset(x, y)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChartPreview() {
    Chart(
        points = listOf(
            Point(2f, 3f),
            Point(4f, 1f),
            Point(3f, 5f),
        ),
        pointsColors = listOf(Color.Red, Color.Green, Color.Blue),
        onChartAction = {},
        onScrollChange = { fl: Float, fl1: Float -> },
        onScaleChange = {},
        scrollX = 0f,
        scrollY = 0f,
        scale = 1f,
    )
}


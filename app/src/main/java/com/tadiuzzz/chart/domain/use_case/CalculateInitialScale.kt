package com.tadiuzzz.chart.domain.use_case

import com.tadiuzzz.chart.domain.model.Point
import kotlin.math.abs

class CalculateInitialScale {

    operator fun invoke(center: Point, points: List<Point>): Float {
        val minX: Float = points.minOf { it.x }
        val maxX: Float = points.maxOf { it.x }
        val minY: Float = points.minOf { it.y }
        val maxY: Float = points.maxOf { it.y }

        val scaleFitMinX = (center.x - 10) / abs(minX)
        val scaleFitMaxX = (center.x - 10) / abs(maxX)
        val scaleFitMinY = (center.y - 10) / abs(minY)
        val scaleFitMaxY = (center.y - 10) / abs(maxY)

        val scale = listOf(
            scaleFitMinX,
            scaleFitMaxX,
            scaleFitMinY,
            scaleFitMaxY
        ).min()
        return scale
    }
}
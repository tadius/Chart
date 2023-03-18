package com.tadiuzzz.chart.presentation.chartScreen

import androidx.compose.ui.graphics.Color
import com.tadiuzzz.chart.domain.model.Point

data class ChartScreenState(
    val points: List<Point> = emptyList(),
    val pointsColors: List<Color>,
    val scale: Float = 1f,
    val scrollX: Float = 0f,
    val scrollY: Float = 0f,
)
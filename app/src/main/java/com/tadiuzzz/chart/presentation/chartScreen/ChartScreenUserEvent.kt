package com.tadiuzzz.chart.presentation.chartScreen

sealed interface ChartScreenUserEvent {
    data class OnScaleChange(val scaleFactor: Float) : ChartScreenUserEvent
    data class OnScrollChange(val xOffset: Float, val yOffset: Float) : ChartScreenUserEvent
}
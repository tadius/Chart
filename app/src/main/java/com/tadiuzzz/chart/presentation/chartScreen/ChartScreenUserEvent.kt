package com.tadiuzzz.chart.presentation.chartScreen

import android.graphics.Bitmap
import android.net.Uri
import com.tadiuzzz.chart.domain.model.Point

sealed interface ChartScreenUserEvent {
    data class OnScaleChange(val scaleFactor: Float) : ChartScreenUserEvent
    data class OnChartInit(val center: Point) : ChartScreenUserEvent
    data class OnScrollChange(val xOffset: Float, val yOffset: Float) : ChartScreenUserEvent
    data class OnSaveChart(val fileUri: Uri?, val bitmap: Bitmap?) : ChartScreenUserEvent
}
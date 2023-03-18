package com.tadiuzzz.chart.presentation.pointsScreen

sealed class PointsScreenUserEvent {
    data class OnPointsCountTextChanged(val text: String) : PointsScreenUserEvent()
    object OnLoadButtonClick : PointsScreenUserEvent()
}
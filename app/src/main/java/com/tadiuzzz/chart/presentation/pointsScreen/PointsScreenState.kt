package com.tadiuzzz.chart.presentation.pointsScreen

data class PointsScreenState(
    val pointsCountText: String = "",
    val isLoading: Boolean = false,
    val errorText: String? = null,
)
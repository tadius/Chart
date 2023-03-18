package com.tadiuzzz.chart.presentation.util

sealed class Screen(val route: String) {
    object PointsScreen: Screen("points_screen")
    object ChartScreen: Screen("chart_screen")
}
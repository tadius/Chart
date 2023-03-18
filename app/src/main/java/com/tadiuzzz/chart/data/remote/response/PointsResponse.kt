package com.tadiuzzz.chart.data.remote.response

data class PointsResponse(
    val points: List<PointResponse>
)

data class PointResponse(
    val x: Float,
    val y: Float
)
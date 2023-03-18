package com.tadiuzzz.chart.domain.repository

import com.tadiuzzz.chart.domain.model.Point

interface PointsRepository {
    fun getLastLoadedPoints(): List<Point>
    suspend fun loadPoints(count: Int)
}
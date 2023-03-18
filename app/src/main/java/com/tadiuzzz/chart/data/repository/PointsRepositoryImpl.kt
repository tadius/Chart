package com.tadiuzzz.chart.data.repository

import com.tadiuzzz.chart.data.remote.PointsApiInterface
import com.tadiuzzz.chart.domain.model.Point
import com.tadiuzzz.chart.domain.repository.PointsRepository

class PointsRepositoryImpl(
    private val api: PointsApiInterface
) : PointsRepository {

    private val points = mutableListOf<Point>()

    override fun getLastLoadedPoints(): List<Point> {
        return points
    }

    override suspend fun loadPoints(count: Int) {
        points.clear()
        val response = api.getPoints(count)
        points.addAll(
            response.points.map {
                Point(it.x, it.y)
            }
        )
    }

}
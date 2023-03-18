package com.tadiuzzz.chart.domain.use_case

import com.tadiuzzz.chart.domain.repository.PointsRepository

class LoadPointsUseCase(
    private val repository: PointsRepository
) {

    suspend operator fun invoke(count: Int) = repository.loadPoints(count)

}
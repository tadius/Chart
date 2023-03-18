package com.tadiuzzz.chart.domain.use_case

import com.tadiuzzz.chart.domain.repository.PointsRepository

class GetLastLoadedPointsUseCase(
    private val repository: PointsRepository
) {

    operator fun invoke() =
        repository.getLastLoadedPoints()
            .sortedBy { it.x }

}
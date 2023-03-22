package com.tadiuzzz.chart.presentation.chartScreen

import androidx.lifecycle.ViewModel
import com.tadiuzzz.chart.domain.use_case.CalculateInitialScale
import com.tadiuzzz.chart.domain.use_case.GetLastLoadedPointsUseCase
import com.tadiuzzz.chart.presentation.util.RandomColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChartScreenViewModel(
    private val getLastLoadedPointsUseCase: GetLastLoadedPointsUseCase,
    private val calculateInitialScale: CalculateInitialScale,
) : ViewModel() {

    val points = getLastLoadedPointsUseCase()

    private val _state = MutableStateFlow<ChartScreenState>(
        ChartScreenState(
            points = points,
            pointsColors = points.map { RandomColor }
        )
    )
    val state = _state.asStateFlow()

    private var minScale = 0f

    fun onUserEvent(event: ChartScreenUserEvent) {
        when (event) {
            is ChartScreenUserEvent.OnScaleChange -> {
                _state.update {
                    val newScale = it.scale * event.scaleFactor
                    it.copy(
                        firstTime = false,
                        scale = when {
                            newScale <= minScale -> it.scale
                            else -> newScale
                        }
                    )
                }
            }
            is ChartScreenUserEvent.OnChartInit -> {
                val scale = calculateInitialScale(event.center, points)
                minScale = scale
                _state.update {
                    it.copy(
                        scale = scale
                    )
                }
            }
            is ChartScreenUserEvent.OnScrollChange -> {
                _state.update {
                    it.copy(
                        firstTime = false,
                        scrollX = it.scrollX + event.xOffset,
                        scrollY = it.scrollY + event.yOffset,
                    )
                }
            }
        }
    }

}
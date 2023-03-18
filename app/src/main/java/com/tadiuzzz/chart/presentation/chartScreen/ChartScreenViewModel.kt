package com.tadiuzzz.chart.presentation.chartScreen

import androidx.lifecycle.ViewModel
import com.tadiuzzz.chart.domain.use_case.GetLastLoadedPointsUseCase
import com.tadiuzzz.chart.presentation.util.RandomColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChartScreenViewModel(
    private val getLastLoadedPointsUseCase: GetLastLoadedPointsUseCase,
) : ViewModel() {

    val points = getLastLoadedPointsUseCase()

    private val _state = MutableStateFlow<ChartScreenState>(
        ChartScreenState(
            points = points,
            pointsColors = points.map { RandomColor }
        )
    )
    val state = _state.asStateFlow()

    fun onUserEvent(event: ChartScreenUserEvent) {
        when (event) {
            is ChartScreenUserEvent.OnScaleChange -> {
                _state.update {
                    it.copy(
                        scale = when {
                            it.scale < 0.5f -> 0.5f
                            else -> it.scale * event.scaleFactor
                        }
                    )
                }
            }
            is ChartScreenUserEvent.OnScrollChange -> {
                _state.update {
                    it.copy(
                        scrollX = it.scrollX + event.xOffset,
                        scrollY = it.scrollY + event.yOffset,
                    )
                }
            }
        }
    }

}
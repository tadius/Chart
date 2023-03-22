package com.tadiuzzz.chart.presentation.chartScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tadiuzzz.chart.R
import com.tadiuzzz.chart.domain.model.Message
import com.tadiuzzz.chart.domain.use_case.CalculateInitialScale
import com.tadiuzzz.chart.domain.use_case.GetLastLoadedPointsUseCase
import com.tadiuzzz.chart.domain.use_case.SaveChartToFileUseCase
import com.tadiuzzz.chart.presentation.util.RandomColor
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ChartScreenViewModel(
    private val getLastLoadedPointsUseCase: GetLastLoadedPointsUseCase,
    private val calculateInitialScale: CalculateInitialScale,
    private val saveChartToFileUseCase: SaveChartToFileUseCase,
) : ViewModel() {

    val points = getLastLoadedPointsUseCase()

    private val _state = MutableStateFlow<ChartScreenState>(
        ChartScreenState(
            points = points,
            pointsColors = points.map { RandomColor }
        )
    )
    val state = _state.asStateFlow()

    private val _messageEvent = MutableSharedFlow<Message>()
    val messageEvent = _messageEvent.asSharedFlow()

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
            is ChartScreenUserEvent.OnSaveChart -> {
                if (event.bitmap == null || event.fileUri == null) {
                    viewModelScope.launch {
                        _messageEvent.emit(Message(textRes = R.string.save_chart_error))
                    }
                } else {
                    viewModelScope.launch {
                        saveChartToFileUseCase(event.fileUri, event.bitmap)
                        _messageEvent.emit(Message(textRes = R.string.save_chart_success))
                    }
                }
            }
        }
    }

}
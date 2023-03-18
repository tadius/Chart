package com.tadiuzzz.chart.presentation.pointsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tadiuzzz.chart.domain.use_case.LoadPointsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PointsScreenViewModel(
    private val loadPointsUseCase: LoadPointsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<PointsScreenState>(PointsScreenState())
    val state = _state.asStateFlow()

    private val _navigateToChartScreenEvent = MutableSharedFlow<Unit>()
    val navigateToChartScreenEvent = _navigateToChartScreenEvent.asSharedFlow()

    fun onUserEvent(event: PointsScreenUserEvent) {
        when (event) {
            is PointsScreenUserEvent.OnPointsCountTextChanged -> onPointsCountChanged(event.text)
            is PointsScreenUserEvent.OnLoadButtonClick -> loadPoints()
        }
    }

    private fun onPointsCountChanged(countText: String) {
        _state.update {
            it.copy(
                pointsCountText = countText,
                errorText = null
            )
        }
    }

    private fun loadPoints() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorText = null) }
            runCatching {
                val pointsCountText = state.value.pointsCountText
                if (pointsCountText.isBlank()) throw NumberFormatException()

                val count = pointsCountText.toInt()
                loadPointsUseCase(count)
                _state.update { it.copy(isLoading = false) }
                _navigateToChartScreenEvent.emit(Unit)
            }.onFailure { ex ->
                val errorText = when (ex) {
                    is NumberFormatException -> "Invalid points count"
                    else -> ex.localizedMessage
                }
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorText = errorText
                    )
                }
            }

        }
    }

}
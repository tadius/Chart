package com.tadiuzzz.chart.presentation.chartScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tadiuzzz.chart.domain.model.Point
import com.tadiuzzz.chart.presentation.chartScreen.components.Chart
import com.tadiuzzz.chart.presentation.chartScreen.components.Table
import com.tadiuzzz.chart.ui.theme.ChartTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChartScreen(
    navController: NavController,
    viewModel: ChartScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    ChartScreenContent(
        state = state,
        onUserEvent = { viewModel.onUserEvent(it) }
    )

}

@Composable
fun ChartScreenContent(
    state: ChartScreenState,
    onUserEvent: (event: ChartScreenUserEvent) -> Unit
) {
    var isTableScrollable by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Table(
            points = state.points,
            isTableScrollable = isTableScrollable,
            others = listOf {
                Chart(
                    points = state.points,
                    pointsColors = state.pointsColors,
                    scrollX = state.scrollX,
                    scrollY = state.scrollY,
                    scale = state.scale,
                    onChartAction = { isTouching ->
                        isTableScrollable = !isTouching
                    },
                    onScrollChange = { xOffset: Float, yOffset: Float ->
                        onUserEvent(ChartScreenUserEvent.OnScrollChange(xOffset, yOffset))
                    },
                    onScaleChange = { scaleFactor ->
                        onUserEvent(ChartScreenUserEvent.OnScaleChange(scaleFactor))
                    }
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChartScreenPreview() {
    ChartTheme {
        ChartScreenContent(
            state = ChartScreenState(
                points = listOf(
                    Point(1f, 3f),
                    Point(3f, 2f),
                    Point(5f, 4f),
                    Point(6f, 8f),
                ),
                pointsColors = listOf(
                    Color.Red, Color.Green, Color.Blue, Color.Yellow
                )
            )
        ) {}
    }
}
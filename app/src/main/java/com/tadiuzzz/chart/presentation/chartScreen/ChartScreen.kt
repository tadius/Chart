package com.tadiuzzz.chart.presentation.chartScreen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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

    val configuration = LocalConfiguration.current

    val isLandscape = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> true
        else -> false
    }

    ChartScreenContent(
        state = state,
        isLandscape = isLandscape,
        onUserEvent = { viewModel.onUserEvent(it) }
    )

}

@Composable
fun ChartScreenContent(
    state: ChartScreenState,
    isLandscape: Boolean,
    onUserEvent: (event: ChartScreenUserEvent) -> Unit
) {
    var isTableScrollable by remember {
        mutableStateOf(true)
    }

    if (isLandscape) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ChartByState(
                modifier = Modifier.weight(0.5f),
                state = state,
                onUserEvent = onUserEvent,
                onChartAction = { isTouching ->
                    isTableScrollable = !isTouching
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            TableByState(
                modifier = Modifier.weight(0.5f),
                state = state,
                isTableScrollable = isTableScrollable
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ChartByState(
                modifier = Modifier.weight(0.5f),
                state = state,
                onUserEvent = onUserEvent,
                onChartAction = { isTouching ->
                    isTableScrollable = !isTouching
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            TableByState(
                modifier = Modifier.weight(0.5f),
                state = state,
                isTableScrollable = isTableScrollable
            )
        }
    }

}

@Composable
fun ChartByState(
    modifier: Modifier = Modifier,
    state: ChartScreenState,
    onUserEvent: (event: ChartScreenUserEvent) -> Unit,
    onChartAction: (isTouching: Boolean) -> Unit,
    ) {
    Chart(
        modifier = modifier,
        points = state.points,
        pointsColors = state.pointsColors,
        scrollX = state.scrollX,
        scrollY = state.scrollY,
        scale = state.scale,
        onChartAction = onChartAction,
        onScrollChange = { xOffset: Float, yOffset: Float ->
            onUserEvent(ChartScreenUserEvent.OnScrollChange(xOffset, yOffset))
        },
        onScaleChange = { scaleFactor ->
            onUserEvent(ChartScreenUserEvent.OnScaleChange(scaleFactor))
        }
    )
}

@Composable
fun TableByState(
    modifier: Modifier = Modifier,
    state: ChartScreenState,
    isTableScrollable: Boolean
) {
    Table(
        modifier = modifier,
        points = state.points,
        isTableScrollable = isTableScrollable
    )
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
            ),
            isLandscape = false
        ) {}
    }
}

@Preview(showBackground = true, widthDp = 960, heightDp = 400)
@Composable
private fun ChartScreenPreviewLandscape() {
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
            ),
            isLandscape = true
        ) {}
    }
}
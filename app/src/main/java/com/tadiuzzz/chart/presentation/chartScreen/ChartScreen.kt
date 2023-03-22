package com.tadiuzzz.chart.presentation.chartScreen

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tadiuzzz.chart.domain.model.Point
import com.tadiuzzz.chart.presentation.chartScreen.components.Chart
import com.tadiuzzz.chart.presentation.chartScreen.components.Table
import com.tadiuzzz.chart.ui.theme.ChartTheme
import dev.shreyaspatil.capturable.controller.CaptureController
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.launch
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

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val captureController = rememberCaptureController()
    var bitmap: Bitmap? by remember {
        mutableStateOf(null)
    }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val fileUri = it.data?.data
            viewModel.onUserEvent(ChartScreenUserEvent.OnSaveChart(fileUri, bitmap))
        }

    LaunchedEffect(key1 = Unit) {
        viewModel.messageEvent.collect { message ->
            val text = message.textRes?.let { context.getString(message.textRes) }
            scope.launch {
                snackBarHostState.showSnackbar(text ?: message.text.orEmpty())
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { snackbarData ->
                    Snackbar(
                        snackbarData = snackbarData
                    )
                }
            )
        }
    ) { paddingValues ->
        ChartScreenContent(
            paddingValues = paddingValues,
            state = state,
            isLandscape = isLandscape,
            captureController = captureController,
            onUserEvent = { viewModel.onUserEvent(it) },
            onChartCaptured = {
                bitmap = it
                val fileName = "Chart_${System.currentTimeMillis()}"
                launcher.launch(
                    Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "image/png"
                        putExtra(Intent.EXTRA_TITLE, fileName)
                    }
                )
            }
        )
    }
}

@Composable
fun ChartScreenContent(
    paddingValues: PaddingValues,
    state: ChartScreenState,
    isLandscape: Boolean,
    captureController: CaptureController,
    onUserEvent: (event: ChartScreenUserEvent) -> Unit,
    onChartCaptured: (bitmap: Bitmap?) -> Unit,
) {
    var isTableScrollable by remember {
        mutableStateOf(true)
    }

    if (isLandscape) {
        Row(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ChartByState(
                modifier = Modifier.weight(0.5f),
                state = state,
                onUserEvent = onUserEvent,
                onChartAction = { isTouching ->
                    isTableScrollable = !isTouching
                },
                captureController = captureController,
                onChartCaptured = onChartCaptured
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
                },
                captureController = captureController,
                onChartCaptured = onChartCaptured
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
    onChartCaptured: (bitmap: Bitmap?) -> Unit,
    captureController: CaptureController
) {

    Chart(
        modifier = modifier,
        points = state.points,
        pointsColors = state.pointsColors,
        scrollX = state.scrollX,
        scrollY = state.scrollY,
        scale = state.scale,
        firstTime = state.firstTime,
        onChartAction = onChartAction,
        onScrollChange = { xOffset: Float, yOffset: Float ->
            onUserEvent(ChartScreenUserEvent.OnScrollChange(xOffset, yOffset))
        },
        onScaleChange = { scaleFactor ->
            onUserEvent(ChartScreenUserEvent.OnScaleChange(scaleFactor))
        },
        onChartInit = { center ->
            onUserEvent(ChartScreenUserEvent.OnChartInit(center))
        },
        onChartCaptured = onChartCaptured,
        captureController = captureController
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
            paddingValues = PaddingValues(),
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
            isLandscape = false,
            captureController = rememberCaptureController(),
            onChartCaptured = {},
            onUserEvent = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 960, heightDp = 400)
@Composable
private fun ChartScreenPreviewLandscape() {
    ChartTheme {
        ChartScreenContent(
            paddingValues = PaddingValues(),
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
            isLandscape = true,
            captureController = rememberCaptureController(),
            onChartCaptured = {},
            onUserEvent = {}
        )
    }
}
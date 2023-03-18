package com.tadiuzzz.chart.presentation.pointsScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tadiuzzz.chart.R
import com.tadiuzzz.chart.presentation.util.Screen
import com.tadiuzzz.chart.ui.theme.ChartTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun PointsScreen(
    navController: NavController,
    viewModel: PointsScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.navigateToChartScreenEvent.collect {
            navController.navigate(Screen.ChartScreen.route)
        }
    }

    PointsScreenContent(
        state = state,
        onUserEvent = { viewModel.onUserEvent(it) }
    )

}

@Composable
fun PointsScreenContent(
    state: PointsScreenState,
    onUserEvent: (event: PointsScreenUserEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.enter_points_title)
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.pointsCountText,
            enabled = !state.isLoading,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                onUserEvent(PointsScreenUserEvent.OnPointsCountTextChanged(it))
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = !state.isLoading,
            onClick = {
                onUserEvent(PointsScreenUserEvent.OnLoadButtonClick)
            }
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(stringResource(R.string.btn_go))
            }
        }
        if (state.errorText != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                text = state.errorText
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PointsScreenPreview() {
    ChartTheme {
        PointsScreenContent(
            state = PointsScreenState()
        ) {}
    }
}
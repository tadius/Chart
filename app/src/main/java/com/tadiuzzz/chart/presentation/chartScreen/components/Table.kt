package com.tadiuzzz.chart.presentation.chartScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tadiuzzz.chart.R
import com.tadiuzzz.chart.domain.model.Point

@Composable
fun Table(
    modifier: Modifier = Modifier,
    isTableScrollable: Boolean,
    points: List<Point>,
    others: List<@Composable () -> Unit> = emptyList()
) {

    TableItem(
        textA = stringResource(R.string.header_x),
        textB = stringResource(R.string.header_y),
        isHeader = true
    )
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        userScrollEnabled = isTableScrollable
    ) {
        points.forEach {
            item {
                TableItem(
                    textA = it.x.toString(),
                    textB = it.y.toString()
                )
            }
        }
        others.forEach {
            item {
                it()
            }
        }

    }

}

@Preview
@Composable
private fun TablePreview() {
    Table(
        isTableScrollable = true,
        points = listOf(
            Point(2f, 3f),
            Point(4f, 1f),
            Point(3f, 5f),
        )
    )
}
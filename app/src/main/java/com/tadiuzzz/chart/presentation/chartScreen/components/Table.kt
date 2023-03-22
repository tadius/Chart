package com.tadiuzzz.chart.presentation.chartScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tadiuzzz.chart.R
import com.tadiuzzz.chart.domain.model.Point

@Composable
fun Table(
    modifier: Modifier = Modifier,
    isTableScrollable: Boolean,
    points: List<Point>
) {

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        TableItem(
            textA = stringResource(R.string.header_x),
            textB = stringResource(R.string.header_y),
            isHeader = true
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            userScrollEnabled = isTableScrollable
        ) {
            points.forEach {
                item {
                    TableItem(
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        textA = it.x.toString(),
                        textB = it.y.toString()
                    )
                }
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
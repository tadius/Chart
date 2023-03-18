package com.tadiuzzz.chart.presentation.chartScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TableItem(
    textA: String,
    textB: String,
    isHeader: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Coordinate(
            modifier = Modifier
                .weight(0.5f),
            text = textA,
            isHeader = isHeader
        )
        Coordinate(
            modifier = Modifier
                .weight(0.5f),
            text = textB,
            isHeader = isHeader
        )
    }
}

@Composable
fun Coordinate(
    modifier: Modifier = Modifier,
    text: String,
    isHeader: Boolean = false
) {
    val padding = if (isHeader) 8.dp else 0.dp
    Text(
        modifier = modifier
            .fillMaxWidth()
            .background(if (isHeader) Color.LightGray else Color.Transparent)
            .padding(vertical = padding),
        text = text,
        fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Medium,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
private fun TableItemPreview() {
    TableItem(textA = "12", textB = "23")
}
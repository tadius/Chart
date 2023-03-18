package com.tadiuzzz.chart.presentation.util

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

val RandomColor
    get() = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))

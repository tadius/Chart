package com.tadiuzzz.chart.domain.model

import androidx.annotation.StringRes

data class Message(
    val text: String? = null,
    @StringRes val textRes: Int? = null,
)
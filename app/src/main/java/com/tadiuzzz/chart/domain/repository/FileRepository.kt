package com.tadiuzzz.chart.domain.repository

import android.graphics.Bitmap
import android.net.Uri

interface FileRepository {
    fun saveChartToFile(fileUri: Uri, bitmap: Bitmap)
}
package com.tadiuzzz.chart.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.tadiuzzz.chart.domain.repository.FileRepository

class FileRepositoryImpl(private val context: Context) : FileRepository {

    override fun saveChartToFile(fileUri: Uri, bitmap: Bitmap) {
        context.contentResolver.openOutputStream(fileUri)?.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
    }

}
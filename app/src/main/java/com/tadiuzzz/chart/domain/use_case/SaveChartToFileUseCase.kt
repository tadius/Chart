package com.tadiuzzz.chart.domain.use_case

import android.graphics.Bitmap
import android.net.Uri
import com.tadiuzzz.chart.domain.repository.FileRepository

class SaveChartToFileUseCase(
    private val fileRepository: FileRepository
) {

    operator fun invoke(fileUri: Uri, bitmap: Bitmap) {
        fileRepository.saveChartToFile(fileUri, bitmap)
    }

}
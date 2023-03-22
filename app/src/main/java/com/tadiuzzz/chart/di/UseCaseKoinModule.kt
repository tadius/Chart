package com.tadiuzzz.chart.di

import com.tadiuzzz.chart.domain.use_case.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseKoinModule = module{
    factoryOf(::LoadPointsUseCase)
    factoryOf(::GetLastLoadedPointsUseCase)
    factoryOf(::IsValidNumber)
    factoryOf(::CalculateInitialScale)
    factoryOf(::SaveChartToFileUseCase)
}
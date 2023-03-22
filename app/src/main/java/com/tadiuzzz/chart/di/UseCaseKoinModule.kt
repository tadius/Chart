package com.tadiuzzz.chart.di

import com.tadiuzzz.chart.domain.use_case.CalculateInitialScale
import com.tadiuzzz.chart.domain.use_case.LoadPointsUseCase
import com.tadiuzzz.chart.domain.use_case.GetLastLoadedPointsUseCase
import com.tadiuzzz.chart.domain.use_case.IsValidNumber
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseKoinModule = module{
    factoryOf(::LoadPointsUseCase)
    factoryOf(::GetLastLoadedPointsUseCase)
    factoryOf(::IsValidNumber)
    factoryOf(::CalculateInitialScale)
}
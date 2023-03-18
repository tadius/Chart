package com.tadiuzzz.chart.di

import com.tadiuzzz.chart.presentation.pointsScreen.PointsScreenViewModel
import com.tadiuzzz.chart.presentation.chartScreen.ChartScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelKoinModule = module {

    viewModelOf(::PointsScreenViewModel)
    viewModelOf(::ChartScreenViewModel)

}
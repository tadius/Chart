package com.tadiuzzz.chart.di

import com.tadiuzzz.chart.data.repository.PointsRepositoryImpl
import com.tadiuzzz.chart.domain.repository.PointsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataKoinModule = module {
    singleOf(::PointsRepositoryImpl) bind PointsRepository::class
}

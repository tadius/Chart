package com.tadiuzzz.chart

import android.app.Application
import com.tadiuzzz.chart.di.dataKoinModule
import com.tadiuzzz.chart.di.networkKoinModule
import com.tadiuzzz.chart.di.useCaseKoinModule
import com.tadiuzzz.chart.di.viewModelKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@App)
            modules(
                listOf(
                    viewModelKoinModule,
                    networkKoinModule,
                    dataKoinModule,
                    useCaseKoinModule,
                )
            )
        }
    }
}
package com.tadiuzzz.chart.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tadiuzzz.chart.BuildConfig
import com.tadiuzzz.chart.data.remote.PointsApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val SERVER_ADDRESS = "https://hr-challenge.interactivestandard.com/api/test/"
private const val CONNECT_TIMEOUT: Long = 120
private const val WRITE_TIMEOUT: Long = 300
private const val READ_TIMEOUT: Long = 120

val networkKoinModule = module {
    single<OkHttpClient> { provideOkHttpClient() }
    single<PointsApiInterface> {
        createRetrofitInstance(get())
            .create(PointsApiInterface::class.java)
    }
}

private fun createRetrofitInstance(
    okHttpClient: OkHttpClient,
): Retrofit = Retrofit.Builder()
    .baseUrl(SERVER_ADDRESS)
    .addConverterFactory(GsonConverterFactory.create(createGsonInstance()))
    .client(okHttpClient)
    .build()

private fun createGsonInstance(): Gson {
    val builder = GsonBuilder()
        .setLenient()
    return builder.create()
}

private fun provideOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    builder
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        builder.addInterceptor(getHttpLoggingInterceptor())
    }
    return builder.build()
}

private fun getHttpLoggingInterceptor() = HttpLoggingInterceptor()
    .apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
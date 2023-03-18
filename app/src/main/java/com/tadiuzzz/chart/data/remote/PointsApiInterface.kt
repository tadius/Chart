package com.tadiuzzz.chart.data.remote

import com.tadiuzzz.chart.data.remote.response.PointsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PointsApiInterface {

    @GET("points")
    suspend fun getPoints(
        @Query("count") count: Int
    ): PointsResponse


}
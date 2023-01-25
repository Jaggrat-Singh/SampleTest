package com.jaggrat.sample.news.data.datasource

import com.jaggrat.sample.news.data.NewsAPIModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retro fit, Often we store apikeys in secure location but this is demo project
 */
interface NewRemoteDataSource {
    @GET("v1/latest-news")
    suspend fun getLatestNews(
        @Query("apiKey") apiKey: String = "dS3WR_WNvAdPjbnVOVgfNBP2xUDOVBAfl3GcTv8byHeNap8F",
    ) : NewsAPIModel
}
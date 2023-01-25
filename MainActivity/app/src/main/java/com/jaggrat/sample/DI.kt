package com.jaggrat.sample

import com.jaggrat.sample.news.data.datasource.NewRemoteDataSource
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Phase 2 Use Hilt for these
private val okHttpClient by lazy {
    val client = OkHttpClient().newBuilder()
    client.build()
}
val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://api.currentsapi.services/")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val dataSource: NewRemoteDataSource = retrofit.create(NewRemoteDataSource::class.java)

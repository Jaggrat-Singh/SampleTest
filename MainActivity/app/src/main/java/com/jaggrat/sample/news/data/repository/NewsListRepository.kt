package com.jaggrat.sample.news.data.repository

import com.jaggrat.sample.news.data.News
import kotlinx.coroutines.flow.Flow


interface NewsListRepository {

    suspend fun getNewsList() : Flow<Result<List<News>>>
}
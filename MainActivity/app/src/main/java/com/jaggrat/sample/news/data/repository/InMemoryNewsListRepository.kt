package com.jaggrat.sample.news.data.repository

import com.jaggrat.sample.news.data.News
import com.jaggrat.sample.news.data.datasource.NewRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 *  In memory collection is used to hold the data.
 *  I could have created usecase/mapper to deal with data mutation but as of now there is no need
 */
class InMemoryNewsListRepository constructor(private val dataSource: NewRemoteDataSource): NewsListRepository {

    override suspend fun getNewsList(): Flow<Result<List<News>>> {
       return flow {
           emit(Result.success(dataSource.getLatestNews().news))
       }.catch {
           emit(Result.failure(RuntimeException("Network error")))
       }
    }
}
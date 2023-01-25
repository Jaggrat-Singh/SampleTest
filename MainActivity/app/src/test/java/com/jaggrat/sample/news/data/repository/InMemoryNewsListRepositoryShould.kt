package com.jaggrat.sample.news.data.repository

import BaseUnitTest
import com.jaggrat.sample.news.data.News
import com.jaggrat.sample.news.data.NewsAPIModel
import com.jaggrat.sample.news.data.datasource.NewRemoteDataSource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import java.lang.RuntimeException

@OptIn(ExperimentalCoroutinesApi::class)
class InMemoryNewsListRepositoryShould : BaseUnitTest() {

    private val dataSource: NewRemoteDataSource = mock()
    private val underTest = InMemoryNewsListRepository(dataSource)
    private val status = "OK";
    private val news = mock<List<News>>()
    private val response: NewsAPIModel = NewsAPIModel(status, news)
    @Test
    fun getNewsListFromDS() = runTest {

        underTest.getNewsList().first()

        verify(dataSource, times(1)).getLatestNews()
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runTest {
        whenever(dataSource.getLatestNews()).thenReturn(response)

        val result = underTest.getNewsList().first()

        Assert.assertEquals(result, Result.success(news))
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runTest {
        whenever(dataSource.getLatestNews()).thenThrow(RuntimeException("Error"))

        val result = underTest.getNewsList().first().exceptionOrNull()?.message

        Assert.assertEquals(result, "Network error")

    }
}
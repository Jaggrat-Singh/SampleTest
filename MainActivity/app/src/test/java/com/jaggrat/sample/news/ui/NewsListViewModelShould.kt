package com.jaggrat.sample.news.ui

import BaseUnitTest
import captureValues
import com.jaggrat.sample.news.data.News
import com.jaggrat.sample.news.data.repository.InMemoryNewsListRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.lang.RuntimeException

@OptIn(ExperimentalCoroutinesApi::class)
class NewsListViewModelShould : BaseUnitTest(){
    private val repository: InMemoryNewsListRepository = mock()
    private val underTest = NewsListViewModel(repository)

    private val expectedRepositoryResponse = mock<List<News>>()
    private val errorMessage = "Error error error"
    private val exception = RuntimeException(errorMessage)

    @Test
    fun getNewsFromRepository() = runTest{
        sampleSuccessResponse()
        underTest.getLatestNews()

        verify(repository, times(1)).getNewsList()
    }

    @Test
    fun emitsNewsListFromRepository() = runTest {
        sampleSuccessResponse()

        underTest.getLatestNews()

        assertEquals(UiState.Success(expectedRepositoryResponse), underTest.uiState().value)
    }

    @Test
    fun emitErrorWhenReceiveError() = runTest {
        sampleErrorResponse()

        underTest.getLatestNews()

        assertTrue(underTest.uiState().value is UiState.Error)
    }

    @Test
    fun emitErrorWhenReceiveEmptyResponse() = runTest {
        sampleEmptyResponse()

        underTest.getLatestNews()

        assertEquals(underTest.uiState().value, UiState.Error("Something went wrong"))
    }

    @Test
    fun showSpinnerWhileLoading() = runTest {
        sampleSuccessResponse()

        underTest.uiState().captureValues {
            underTest.getLatestNews()
            assertEquals(UiState.Loading, values[0])
        }
    }

    @Test
    fun closeLoaderAfterError() = runTest{
        sampleErrorResponse()
        underTest.uiState().captureValues {
            underTest.getLatestNews()

            assertNotSame(values.last(), UiState.Loading)
        }
    }

    @Test
    fun closeLoaderAfterNewsListLoaded() = runTest {
        sampleSuccessResponse()

        underTest.uiState().captureValues {
            underTest.getLatestNews()
            assertFalse(values.last() is UiState.Loading)
        }
    }

    private suspend fun sampleSuccessResponse() {
        whenever(repository.getNewsList()).thenReturn(
            flow {
                emit(Result.success(expectedRepositoryResponse))
            }
        )
    }

    private suspend fun sampleErrorResponse() {
        whenever(repository.getNewsList()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )
    }

    private suspend fun sampleEmptyResponse() {
        whenever(repository.getNewsList()).thenReturn(
            flow {
                emit(Result.success(emptyList()))
            }
        )
    }
}
package com.jaggrat.sample.news.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jaggrat.sample.news.data.repository.NewsListRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * VM is using repo interface to get the data for UI.
 * I did not have time to implement DI but I have designed classes in such a way
 * there is no need to change the architecture apart from adding annotations
 */
class NewsListViewModel(private val repository: NewsListRepository): BaseViewModel<UiState>() {

    fun getLatestNews() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getNewsList().first()
                val latestNews = response.getOrNull() ?: emptyList()
                if (latestNews.isNotEmpty()) {
                    uiState.value = UiState.Success(latestNews)
                } else {
                    val message = response.exceptionOrNull()?.message ?: "Something went wrong"
                    uiState.value = UiState.Error(message)
                }
            } catch (ex: Exception) {
                uiState.value = UiState.Error(ex.message.toString())
            }

        }
    }
}
package com.jaggrat.sample.news.ui

import com.jaggrat.sample.news.data.News

sealed class UiState {
    object Loading : UiState()
    data class Success(val latestNews: List<News>) : UiState()
    data class Error(val message: String) : UiState()
}
package com.jaggrat.sample.news.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.jaggrat.sample.news.data.repository.NewsListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlin.reflect.KClass

class ViewModelFactory(
    private val repository: NewsListRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            NewsListRepository::class.java,
        ).newInstance(repository)
    }

}
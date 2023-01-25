package com.jaggrat.sample.news.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaggrat.sample.R
import com.jaggrat.sample.dataSource
import com.jaggrat.sample.news.data.News
import com.jaggrat.sample.news.data.repository.InMemoryNewsListRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val repository = InMemoryNewsListRepository(dataSource)
    private var viewModelFactory = ViewModelFactory(repository)

    lateinit var viewModel: NewsListViewModel


    private lateinit var newAdapter : NewsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNewsView()
        initViewModel()
        // Since this method is called in configuration change cycle hence you would see multiple API call
        viewModel.getLatestNews()

        viewModel.uiState().observe(this as LifecycleOwner) { state ->
            showCurrentState(state)
        }
    }

    private fun showCurrentState(state: UiState) {
        when (state) {
            is UiState.Success -> setUpNewsList(state.latestNews)
            is UiState.Error -> showError(state.message)
            is UiState.Loading -> showLoading()
        }
    }

    private fun setUpNewsList(latestNews: List<News>) {
        newAdapter.submitList(latestNews)
        loader.visibility = View.GONE
        error_tv.visibility = View.GONE
    }



    private fun showLoading() {
        loader.visibility = View.VISIBLE
        error_tv.visibility = View.GONE
    }

    private fun showError(error: String) {
        val errorTv= findViewById<TextView>(R.id.error_tv)
        errorTv.text = error
        errorTv.visibility = View.VISIBLE
        loader.visibility = View.GONE
    }

    private fun initNewsView() {
        recyclerView = findViewById<RecyclerView>(R.id.news_list)
        newAdapter = NewsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = newAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsListViewModel::class.java]
    }
}
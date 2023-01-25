package com.jaggrat.sample.news.data


// depending on situation we may think of creating special Models as per UI need to make seperation of concern
data class NewsAPIModel(val status: String, val news: List<News>)

data class News(val id: String, val title: String, val image: String)

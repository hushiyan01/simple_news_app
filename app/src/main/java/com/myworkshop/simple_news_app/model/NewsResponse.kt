package com.myworkshop.simple_news_app.model

data class NewsResponse(
    val news: List<NewsAttributes>,
    val page: Int,
    val status: String
)
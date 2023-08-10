package com.myworkshop.simple_news_app.model

interface ResponseCallBack {

    fun success(newsResponse: NewsResponse)
    fun failure(error: String)

}
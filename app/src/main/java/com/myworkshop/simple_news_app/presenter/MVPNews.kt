package com.myworkshop.simple_news_app.presenter

import com.myworkshop.simple_news_app.model.News
import com.myworkshop.simple_news_app.model.NewsResponse

interface MVPNews {
    interface INewsPresenter{
        fun getNews()
        fun getNewsByKeywords(keyword:String)
    }

    interface NewsView{
        fun setSuccess(newsResponse: NewsResponse)
        fun setFailure(error:String)
    }

    interface FavoritesView{
        fun loadFromDao(list: List<News>)
    }
}
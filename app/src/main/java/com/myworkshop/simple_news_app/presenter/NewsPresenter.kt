package com.myworkshop.simple_news_app.presenter

import com.myworkshop.simple_news_app.model.NewsResponse
import com.myworkshop.simple_news_app.model.ResponseCallBack
import com.myworkshop.simple_news_app.model.VolleyHandler

class NewsPresenter(private val volleyHandler: VolleyHandler, private val news: MVPNews.NewsView) :
    MVPNews.INewsPresenter {

    override fun getNews() {
        volleyHandler.fetchNewsResponse(responseCallback =
        object : ResponseCallBack {
            override fun success(newsResponse: NewsResponse) {
                news.setSuccess(newsResponse)
            }

            override fun failure(error: String) {
                news.setFailure(error)
            }

        }
        )
    }

    override fun getNewsByKeywords(keyword: String) {
        volleyHandler.fetchNewsResponseByKeyWords(keyword,
            object : ResponseCallBack {
                override fun success(newsResponse: NewsResponse) {
                    news.setSuccess(newsResponse)
                }

                override fun failure(error: String) {
                    news.setFailure(error)
                }

            }
        )
    }

}
package com.myworkshop.simple_news_app.presenter

import com.myworkshop.simple_news_app.model.db.NewsDao

class FavoriteNewsPresenter(private val dao: NewsDao, private val favoritesView: MVPNews.FavoritesView): MVPNews.INewsPresenter{

    override fun getNews() {
        favoritesView.loadFromDao(dao.getAllNews())
    }

    override fun getNewsByKeywords(keyword: String) {

    }
}
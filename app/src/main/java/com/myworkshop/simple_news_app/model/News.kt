package com.myworkshop.simple_news_app.model

data class News (val id:Long, var isFavorite:Boolean = false, val newsAttributes:NewsAttributes)
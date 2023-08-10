package com.myworkshop.simple_news_app.model.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.myworkshop.simple_news_app.model.db.DBConstants.CREATE_TABLE_NEWS

class NewsDBHelper(private val context: Context) : SQLiteOpenHelper(context,
    DBConstants.DB_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_NEWS)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


}
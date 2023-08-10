package com.myworkshop.simple_news_app.model.db

import android.content.ContentValues
import android.database.Cursor
import com.myworkshop.simple_news_app.model.News
import com.myworkshop.simple_news_app.model.NewsAttributes

class NewsDao(private val dbHelper: NewsDBHelper) {

    fun getAllNews():List<News>{
        val res = ArrayList<News>()
        val cursor = dbHelper.readableDatabase.query(
            DBConstants.TABLE_NAME_NEWS,
            null,
            null,
            null,
            null,
            null,
            null,
            null)
        while (cursor.moveToNext()){
            val news = getNewsFromCursor(cursor)
            res.add(news)
        }
        cursor.close()
        return res
    }

    fun delete(id:String):Int{
        val selection = "gen_id = ?"
        val selectionArgs = arrayOf(id)

        return dbHelper.writableDatabase.delete(DBConstants.TABLE_NAME_NEWS,selection, selectionArgs)
    }

    fun save(newsAttributes: NewsAttributes):Long{
        val contentValues = ContentValues()
        contentValues.put("author", newsAttributes.author)
        contentValues.put("category", newsAttributes.category.toStringWithComma())
        contentValues.put("description", newsAttributes.description)
        contentValues.put("image", newsAttributes.image)
        contentValues.put("language", newsAttributes.language)
        contentValues.put("published", newsAttributes.published)
        contentValues.put("title", newsAttributes.title)
        contentValues.put("title", newsAttributes.title)
        contentValues.put("url", newsAttributes.url)
        contentValues.put("gen_id", newsAttributes.id)
        return dbHelper.writableDatabase.insert(DBConstants.TABLE_NAME_NEWS, null, contentValues)
    }

    fun isFavorite(genId:String):Boolean{
        val cursor = dbHelper.readableDatabase.query(
            DBConstants.TABLE_NAME_NEWS,
            null,
            "gen_id = ?",
            arrayOf(genId),
            null,
            null,
            null,
            null)
        val res = cursor.moveToNext()
        cursor.close()
        return res
    }


    private fun getNewsFromCursor(cursor: Cursor): News {
        cursor.apply {
            val id = getLong(getColumnIndexOrThrow("id"))
            val author = getString(getColumnIndexOrThrow("author"))
            val category = getString(getColumnIndexOrThrow("category"))
            val description = getString(getColumnIndexOrThrow("description"))
            val image = getString(getColumnIndexOrThrow("image"))
            val language = getString(getColumnIndexOrThrow("language"))
            val published = getString(getColumnIndexOrThrow("published"))
            val title = getString(getColumnIndexOrThrow("title"))
            val url = getString(getColumnIndexOrThrow("url"))
            val genId = getString(getColumnIndexOrThrow("gen_id"))
            return News(id, true, NewsAttributes(
                author,
                category.split(","),
                description,
                genId,
                image,
                language,
                published,
                title,
                url
            ))
        }
    }
}

fun List<String>.toStringWithComma():String{
    val res = StringBuffer()
    return this.map { it.trim() }.joinToString(separator = ",")
}